import React, { useRef, useState, useMemo, useEffect } from 'react';
import styled from 'styled-components';
import * as THREE from 'three'
import { Canvas, useFrame } from '@react-three/fiber'
import { Text, TrackballControls, FontData  } from '@react-three/drei'
import { useSelector } from 'react-redux';
import http from 'api/commonHttp'
import { useDispatch } from 'react-redux';
import { setWord } from 'reducer/wordReducer';

function Word({onSelected, num, children, ...props }) {
  const color = new THREE.Color()
  const fontProps = { font: '/PretendardVariable.ttf', fontSize: 2.5+num/10*6, letterSpacing: -0.05, lineHeight: 1, 'material-toneMapped': false }
  const ref = useRef()
  const [hovered, setHovered] = useState(false)
  const over = (e) => (e.stopPropagation(), setHovered(true))
  const out = () => setHovered(false)
  // Change the mouse cursor on hover
  useEffect(() => {
    if (hovered) document.body.style.cursor = 'pointer'
    return () => (document.body.style.cursor = 'auto')
  }, [hovered])
  // Tie component to the render-loop
  useFrame(({ camera }) => {
    // Make text face the camera
    ref.current.quaternion.copy(camera.quaternion)
    // Animate font color
    ref.current.material.color.lerp(color.set(hovered ? 'rgb(73, 4, 82)' : '#6A74C9'), 0.1)
  })
  return <Text ref={ref} onPointerOver={over} onPointerOut={out} onClick={(event) => onSelected(event.eventObject._textRenderInfo.parameters.text
)} {...props}  {...fontProps} children={children.word} />
}

function Cloud({ wordclouds, count = 200, radius = 20, onSelected }) {
  // Create a count x count random words with spherical distribution  
  const words = useMemo(() => {
    if(wordclouds.length === 0) return;
    const temp = []
    const spherical = new THREE.Spherical()
    const phiSpan = Math.PI / (count + 1)
    const thetaSpan = (Math.PI * 2) / count
    let cnt = 0;
    for (let i = 1; i <count + 1; i++)
      for (let j = 0; j < count; j++) {
        temp.push([new THREE.Vector3().setFromSpherical(spherical.set(radius, phiSpan * i, thetaSpan * j)), wordclouds[cnt]])
        cnt++; 
        if(cnt >= wordclouds.length) cnt=0;
      }
    return temp
  }, [wordclouds, count, radius])

  return words === undefined ? null : words.map(([pos, word], index) => <Word key={index} position={pos} children={word} num={index} onSelected={onSelected} />);
}

export default function WordCloudWords() {

  const cloud = useSelector((state) => state.cloud);
  const [words, setWords] = useState([]);
  const dispatch = useDispatch();
  const onSelected = (word) => {
    dispatch(setWord(word));
  }
  useEffect(()=> {
    if(cloud.date !== '') {
      http.get(`/wordcloud?category=${cloud.kind}&date=${cloud.date}`).then(({data}) => {
        if(data) {
          data.sort((a,b)=> a.count-b.count);
          dispatch(setWord(data[data.length-1].word));
        }
        setWords(data);
      })
    }
  },[cloud])

  return (
      <Container>
         <Canvas dpr={[1, 2]} camera={{ position: [0, 0, 35], fov: 90 }} style={{minHeight:'600px'}}>
                <fog attach="fog" args={['#202025', 0, 80]} />
                <Cloud  wordclouds={words} count={4} radius={21} onSelected={onSelected}/>
                <TrackballControls />
            </Canvas>
      </Container>
  )
}

const Container = styled.div`

`;
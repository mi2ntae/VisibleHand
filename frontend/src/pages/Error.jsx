import { MathUtils } from 'three'
import { useRef } from 'react'
import { Canvas, useFrame } from '@react-three/fiber'
import { Instances, Instance, Environment } from '@react-three/drei'
import { EffectComposer, N8AO, TiltShift2 } from '@react-three/postprocessing'
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom'
import Login from './Login'

const particles = Array.from({ length: 150 }, () => ({
  factor: MathUtils.randInt(20, 100),
  speed: MathUtils.randFloat(0.01, 0.75),
  xFactor: MathUtils.randFloatSpread(40),
  yFactor: MathUtils.randFloatSpread(10),
  zFactor: MathUtils.randFloatSpread(10)
}))

export default function Onboarding() {
  const navigate  = useNavigate();
  const onClick = () => {
    navigate("/news");
  }
  return (
    <Container>
      <Title> 
        <Sub>
          404 NOT FOUND
          {/* <Login></Login> */}
        </Sub>
        <JOIN onClick={onClick}>홈으로</JOIN>
      </Title>
      <Canvas shadows dpr={[1, 2]} gl={{ antialias: false }} camera={{ fov: 50, position: [0, 0, 20] }}>
        <color attach="background" args={['#eff4ff']} />
        <fog attach="fog" args={['red', 20, -5]} />
        <ambientLight intensity={5.5} />
        <pointLight position={[10, 10, 10]} intensity={1} castShadow />
        <Bubbles />
        <EffectComposer disableNormalPass>
          <N8AO aoRadius={6} intensity={2} color="red" />
          <TiltShift2 blur={0.2} />
        </EffectComposer>
        <Environment preset="city" />
      </Canvas>
    </Container>
  )
}

function Bubbles() {
  const ref = useRef()
  useFrame((state, delta) => void (ref.current.rotation.y = MathUtils.damp(ref.current.rotation.y, (-state.mouse.x * Math.PI) / 6, 2.75, delta)))
  return (
    <Instances limit={particles.length} ref={ref}  position={[0, 2.5, 0]}>
      <sphereGeometry args={[0.45, 64, 64]} />
      <meshStandardMaterial roughness={1} color="#6A74C9" />
      {particles.map((data, i) => (
        <Bubble key={i} {...data} />
      ))}
    </Instances>
  )
}

function Bubble({ factor, speed, xFactor, yFactor, zFactor }) {
  const ref = useRef()
  useFrame((state) => {
    const t = factor + state.clock.elapsedTime * (speed / 2)
    ref.current.scale.setScalar(Math.max(1.5, Math.cos(t) * 5))
    ref.current.position.set(
      Math.cos(t) + Math.sin(t * 1) / 10 + xFactor + Math.cos((t / 10) * factor) + (Math.sin(t * 1) * factor) / 10,
      Math.sin(t) + Math.cos(t * 2) / 10 + yFactor + Math.sin((t / 10) * factor) + (Math.cos(t * 2) * factor) / 10,
      Math.sin(t) + Math.cos(t * 2) / 10 + zFactor + Math.cos((t / 10) * factor) + (Math.sin(t * 3) * factor) / 4
    )
  })
  return <Instance ref={ref} />
}

const Container = styled.div`
position: absolute;
width:100%;
height: 100vh;
left : 0;
`;

const Title = styled.div`
z-index : 1;
position: absolute;
top : 45%;
left : 28%;
text-align: center;
`;

const Sub = styled.div`
    text-align: center;
    font-weight : 600;
    float : center;
    width: 100%;
    font-size : 100px;
    color : #6A74C9;
`;

const JOIN = styled.div`
// z-index : 1;
width: 50%;
margin-left: 20%;
// position: absolute;
// bottom : 10%;
// left : 50%;
font-size : 32px;
font-weight: 600;
border : 3px solid #6A74C9;
margin-top : 50px;
// padding : 10px;
color : #6A74C9;
transition: 0.1s;
text-align: center;
cursor : pointer;
&:hover {
  background: #6A74C9;
  color: white;
  transition: 0.5s;
}
`;
import * as React from 'react';
import PropTypes from 'prop-types';
import LinearProgress from '@mui/material/LinearProgress';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import { useEffect, useState } from 'react';
import styled from "styled-components";


function LinearProgressWithLabel(props) {
    return (
      <Box sx={{ display: 'flex', alignItems: 'center' }}>
        <Box sx={{ width: '100%', mr: 1}}>
          <ProgressBar variant="determinate" {...props}/>
        </Box>
        <Box sx={{ minWidth: 35 }}>
          <Typography variant="body2" color={props.value > 60 ? "red":"text.secondary"}>{`${30 - Math.round(
            props.value/3.3,
          )}초`}</Typography>
        </Box>
      </Box>
    );
  }

  LinearProgressWithLabel.propTypes = {
    /**
     * The value of the progress indicator for the determinate and buffer variants.
     * Value between 0 and 100.
     */
    value: PropTypes.number.isRequired,
  };
export default function ProgressTimeBar({mark, time}) {
    const [progress, setProgress] = useState(0);

    useEffect(() => {
      const timer = setInterval(() => {
        setProgress((prevProgress) => (prevProgress >= 97 ? mark() : prevProgress + 3.3));
      }, 1000);
      return () => {
        clearInterval(timer);
        setProgress(0)
      };
    }, [time]);
  
    return (
      (time ? 
        <Box sx={{ width: '100%' }}>
        <LinearProgressWithLabel value={progress} />
      </Box> : null)
      
    );
}

const ProgressBar = styled(LinearProgress)`
margin: 16px 0;
  min-height: 20px;
  border-radius : 1em;
  background-color: white !important;
  
  .MuiLinearProgress-barColorPrimary {
    background-color: #A8B1FF;
  }
`;


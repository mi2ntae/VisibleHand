import * as React from "react";
import PropTypes from "prop-types";
import LinearProgress from "@mui/material/LinearProgress";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import { useEffect, useState, useRef } from "react";
import styled from "styled-components";

function LinearProgressWithLabel(props) {
  return (
    <Box sx={{ display: "flex", alignItems: "center" }}>
      <Box sx={{ width: "100%", mr: 1 }}>
        <ProgressBar variant="determinate" {...props} />
      </Box>
      <Box sx={{ minWidth: 35 }}>
        <Typography
          variant="body2"
          color={props.value > 60 ? "red" : "text.secondary"}
        >{`${30 - Math.round(props.value / 3.3)}초`}</Typography>
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
export default function ProgressTimeBar({ mark, time, wordId }) {
  const [progress, setProgress] = useState(0);
  const [timer, setTimer] = useState(null);
  useEffect(() => {
    if(wordId == 0) return;
    setTimer(setInterval(() => {
      setProgress((prevProgress) =>
        prevProgress + 3.3
      );
    }, 100));
    return () => {
      clearInterval(timer);
      setProgress(0);
    };
  }, [wordId]);

  useEffect(() => {
    if(progress >= 97) {
      clearInterval(timer);
      setProgress(0);
      if(time) mark();
    }
  }, [progress]);

  return time ? (
    <Box sx={{ width: "100%" }}>
      <LinearProgressWithLabel value={progress} />
    </Box>
  ) : null;
}

const ProgressBar = styled(LinearProgress)`
  margin: 16px 0;
  min-height: 20px;
  border-radius: 1em;
  background-color: white !important;

  .MuiLinearProgress-barColorPrimary {
    background-color: #a8b1ff;
  }
`;

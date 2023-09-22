import { Background, BannerTitle } from 'styled';
import styled from 'styled-components';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { Doughnut } from 'react-chartjs-2';

ChartJS.register(ArcElement, Tooltip, Legend);

export default function MyPageWordQuizChart({data}) {
    const doughnutData = {
    labels: ["경제", "사회", "과학", "경영", "공공", "금융", "기타"],
    datasets: [
        {
        label: 'solved ',
        data: data,
        backgroundColor: [
            'rgba(255, 99, 132, 0.2)',
            'rgba(54, 162, 235, 0.2)',
            'rgba(255, 206, 86, 0.2)',
            'rgba(75, 192, 192, 0.2)',
            'rgba(153, 102, 255, 0.2)',
            'rgba(255, 159, 64, 0.2)',
            'rgba(204, 255, 255, 0.9)',
        ],
        borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(54, 162, 235, 1)',
            'rgba(255, 206, 86, 1)',
            'rgba(75, 192, 192, 1)',
            'rgba(153, 102, 255, 1)',
            'rgba(255, 159, 64, 1)',
            'rgba(204, 255, 255, 1)',
          ],
          borderWidth: 0,
        }
    ]
    };

    return (
        <Doughnut data={doughnutData} style={{width: "230px", maxHeight: "250px"}} options={{ maintainAspectRatio: false }}></Doughnut>
    );
}
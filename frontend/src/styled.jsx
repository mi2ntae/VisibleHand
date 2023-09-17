import styled from "styled-components";
import { black_grey, lightest_grey, white } from "lib/style/colorPalette";
import { Link } from "react-router-dom";

export const Background = styled.div`
  background-color: ${white};
  border-radius: 16px;
  border: 1px solid ${lightest_grey};
  display: flex;
  flex-direction: column;
  color: ${black_grey};
  padding: 32px;
`;

export const BannerTitle = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  margin-bottom: 0.5rem;
  font-size: 1.25rem;
  font-weight: 600;
`;

export const BannerElement = styled(Link)`
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 1rem;
  text-decoration: none;
  color: ${black_grey};
`;

export const ProfileImg = styled.img`
  width: 40px;
  height: 40px;
  border-radius: 40px;
  object-fit: cover;
`;

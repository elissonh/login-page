import { styled } from 'styled-components'

const CustomHyperLink = styled.a`
  color: black;
  text-decoration: none;
  cursor: pointer;
  &:visited {
    none;
  }
  &:hover {
    color: #7699AA;
    text-decoration: underline;
  }
`;

export default CustomHyperLink;
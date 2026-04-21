import { styled } from 'styled-components'

const Button = styled.button`
  background-color: #7699AA;
  color: white;
  border: none;
  border-radius: 22px;
  padding: .75rem;
  text-transform: uppercase;
  transition: background-color .3s;
  font-weight: bold;
  box-shadow: 2px 2px 6px rgba(0, 0, 0, .3);
  &:hover {
    background-color: #516874
  }
`;

export default Button;
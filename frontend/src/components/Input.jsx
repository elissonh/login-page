import {styled} from 'styled-components'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  margin-top: 1rem;
  border-bottom: 1px solid grey;
`
const Label = styled.label`
  text-transform: capitalize;
  color: ${({$invalid}) => $invalid ? "#f94a4a" : 'black' };
`
const InputContainer = styled.div`
  display: flex;
  align-items: center;
  font-size: .75rem;
  color: grey;
  gap: .25rem;
  padding-left: .25rem;
  border: ${({$invalid}) => $invalid ? '1px solid #f94a4a' : 'none' };
  // ${({$invalid}) => $invalid ? 'background-color: #fed2d2' : '' };
`
const Input = styled.input`
  border: none;
  padding: .75rem 0;
  width: 100%;
  &:focus {
    outline: none;
  };
  &:-webkit-autofill,
  &:-webkit-autofill:hover,
  &:-webkit-autofill:focus {
    -webkit-box-shadow: 0 0 0px 1000px white inset;
    box-shadow: 0 0 0px 1000px white inset;
  }
`

export default function CustomInput({label, icon, invalid, ...props}){
  return (
    <Container>
      <Label htmlFor={label} $invalid={invalid}>{label}</Label>
      <InputContainer $invalid={invalid}>
        <FontAwesomeIcon icon={icon} />
        <Input id={label} autoComplete={label} {...props}/>
      </InputContainer>
    </Container>
  )
}
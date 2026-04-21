import { useState } from 'react'
import { styled } from 'styled-components'
import { faUser, faLock, faAt } from '@fortawesome/free-solid-svg-icons';

import CustomForm from './Form.jsx';
import CustomHyperLink from './Hyperlink.jsx';
import CustomInput from './Input.jsx';
import InnerFormContainer from './InnerFormContainer.jsx';
import Button from './Button.jsx';
import ErrorSpan from './Error.jsx';

import { register } from '../api.js';

const InnerErrorSpan = styled.span`
  color: red;
  font-size: .75rem;
`;

export default function CreateAccount({ onNavigate, onRegisterSuccess }){
  const [enteredUser, setEnteredUser] = useState('');
  const [enteredEmail, setEnteredEmail] = useState('');
  const [enteredPassword, setEnteredPassword] = useState('');
  const [enteredConfirmPassword, setConfirmPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(undefined);
  const [submitted, setSubmitted] = useState(false);
  
  function handleInputChange(identifier, value) {
    if (identifier === 'username') {
      setEnteredUser(value);
    } else if (identifier === 'email') {
      setEnteredEmail(value);
    } else if (identifier === 'password') {
      setEnteredPassword(value);
    } else {
      setConfirmPassword(value);
    }
  }

  async function handleCreateAccount(event) {
    event.preventDefault();
    setSubmitted(true);

    const isValid = (
      enteredUser.trim().length > 0 &&
      enteredEmail.includes('@') &&
      enteredPassword.trim().length >= 4 &&
      enteredConfirmPassword.trim().length >= 4 &&
      enteredPassword === enteredConfirmPassword
    );

    if (!isValid) return;

    setError(undefined);
    setLoading(true);
    const result = await register(enteredEmail, enteredUser, enteredPassword);
    setLoading(false);

    if (result.success) {
      onRegisterSuccess();
    } else {
      setError(result.error);
    }
  }

    const userInvalid = submitted && enteredUser.trim().length < 3;
    const emailInvalid = submitted && !enteredEmail.includes('@');
    const passwordInvalid = submitted && enteredPassword.trim().length < 4;
    const confirmPasswordInvalid = submitted && enteredConfirmPassword.trim().length < 4;
    const passwordMismatch = submitted && enteredPassword !== enteredConfirmPassword;

    return (
      <CustomForm className='main-container' onSubmit={handleCreateAccount}>
        <h1>Create Account</h1>
        <InnerFormContainer className='form-container'>
          <CustomInput
            label="username"
            icon={faUser}
            invalid={userInvalid}
            type="text"
            placeholder="Type your username"
            onChange={(event) => handleInputChange('username', event.target.value)}
          />
          {userInvalid &&
            <InnerErrorSpan>Username must have at least 3 digits.</InnerErrorSpan>
          }
          <CustomInput
            label="email"
            icon={faAt}
            invalid={emailInvalid}
            type="email"
            placeholder="Type your email"
            onChange={(event) => handleInputChange('email', event.target.value)}
          />
          <CustomInput
            label="password"
            icon={faLock}
            invalid={passwordInvalid}
            type="password"
            placeholder="Type your password"
            onChange={(event) => handleInputChange('password', event.target.value)}
          ></CustomInput>
          <CustomInput
            label="confirm password"
            icon={faLock}
            invalid={(confirmPasswordInvalid || passwordMismatch)}
            type="password"
            placeholder="Confirm your password"
            onChange={(event) => handleInputChange('confirm-password', event.target.value)}
          ></CustomInput>
            {passwordMismatch &&
                <InnerErrorSpan>Passwords do not match.</InnerErrorSpan>
            }
          </InnerFormContainer>
          {error && <ErrorSpan>{error}</ErrorSpan>}
          <InnerFormContainer className='form-container'>
            <Button type="submit" disabled={loading}>create account</Button>
          </InnerFormContainer>
          <span>
            I Already have an account.
            <CustomHyperLink
              href='#'
              onClick={(event) => { event.preventDefault(); onNavigate(); }}>
                Sign In</CustomHyperLink>
          </span>
      </CustomForm>
    );
}
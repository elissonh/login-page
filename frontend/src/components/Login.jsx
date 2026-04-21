import { useState } from 'react'
import { styled } from 'styled-components'
import { faUser, faLock, faL } from '@fortawesome/free-solid-svg-icons';

import CustomForm from './Form.jsx';
import CustomHyperLink from './Hyperlink.jsx';
import CustomInput from './Input.jsx';
import InnerFormContainer from './InnerFormContainer.jsx';
import Button from './Button.jsx';
import ErrorSpan from './Error.jsx';

import { login } from '../api.js';


export default function Login({ onNavigate, onLoginSuccess }){
  const [enteredEmail, setEnteredEmail] = useState('');
  const [enteredPassword, setEnteredPassword] = useState('');
  const [submitted, setSubmitted] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(undefined);

  function handleInputChange(identifier, value) {
    if (identifier === 'email') {
      setEnteredEmail(value);
    } else {
      setEnteredPassword(value);
    }
  }

  async function handleLogin(event) {
    event.preventDefault();
    setSubmitted(true);

    setLoading(true);
    const result = await login(enteredEmail, enteredPassword);
    setLoading(false);

    if (result.success) {
      onLoginSuccess();
    } else {
      setError(result.error);
    }
  }

  const emailInvalid = submitted && !enteredEmail.includes('@'); 
  const passwordInvalid = submitted && enteredPassword.trim().length === 0;

  return (
    <CustomForm className='main-container' onSubmit={handleLogin}>
      <h1>Login</h1>
      <InnerFormContainer>
        <CustomInput
            label="email"
            icon={faUser}
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
      </InnerFormContainer>
      {error && <ErrorSpan>{error}</ErrorSpan>}
      <InnerFormContainer className='form-container'>
        <Button type="submit" disabled={loading}>login</Button>
      </InnerFormContainer>
      <span>
        Don't have an account?
        <CustomHyperLink 
            href='#'
            onClick={(event) => { event.preventDefault(); onNavigate(); }}>Create One</CustomHyperLink>
      </span>
    </CustomForm>
  );
}
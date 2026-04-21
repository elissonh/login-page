import { useState } from 'react'
import { styled } from 'styled-components'

import CreateAccount from './components/CreateAccount.jsx'
import Login from './components/Login.jsx'
import Home from './components/Home.jsx'

const Main = styled.main`
  height: 100dvh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px;
`

function App() {
  const [page, setPage] = useState(() => {
    if (localStorage.getItem("username")) return 'welcome';
    return localStorage.getItem("page") ?? 'login';
  });

  function navigateTo(newPage) {
    localStorage.setItem("page", newPage);
    setPage(newPage);
  }

  if (page === 'welcome') {
    return (
      <Home
        username={localStorage.getItem("username")}
        onLogout={() => {
          localStorage.removeItem("username");
          localStorage.removeItem("page");
          navigateTo('login');
        }}
      />
    );
  }

  return (
    <Main>
      {page === 'login'
        ? <Login
            onNavigate={() => navigateTo('createAccount')}
            onLoginSuccess={() => navigateTo('welcome')}
          />
        : <CreateAccount
            onNavigate={() => navigateTo('login')}
            onRegisterSuccess={() => navigateTo('login')}
          />
      }
    </Main>
  );
}

export default App

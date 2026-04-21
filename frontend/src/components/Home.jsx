import { styled } from 'styled-components'

import CustomHyperLink from './Hyperlink.jsx';

const GreetingH2 = styled.h2`
  color: #56686F;
  font-size: 2rem;
`;

const GreetingH1 = styled.h1`
  color: #342A45;
  font-size: 3rem;
`;

const HomeMain = styled.main`
  height: 100dvh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px;
  background: linear-gradient(45deg, #947766, #eee0a9, #887158);
`

export default function Home({ username, onLogout }){

    function handleLogout(){
      event.preventDefault();
      localStorage.removeItem("username");

      onLogout();
    }

    return (
    <HomeMain>
        <GreetingH2>Welcome</GreetingH2>
        <GreetingH1>{username}</GreetingH1>
        <CustomHyperLink
          href="#"
          onClick={handleLogout}
        >Logout</CustomHyperLink>
      </HomeMain>
    );
}
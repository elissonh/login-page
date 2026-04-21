const API_URL = import.meta.env.VITE_API_URL;

export async function login(email, password) {
  try {
    const response = await fetch(`${API_URL}/api/auth/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ "email": email, "password": password }),
    });

    const data = await response.json();

    if (response.ok) {
      // Save username
      localStorage.setItem("username", data.username);
      return { success: true };
    } else {
      return { success: false, error: data.message };
    }
  } catch (err) {
    return { success: false, error: "Could not connect to the server. Try again later." };
  }
    return false;
}

export async function register(email, username, password) {
  try {
    const response = await fetch(`${API_URL}/api/auth/register`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({"username": username, "email": email, "password": password}),
    });

    const data = await response.json();

    if (response.ok) {
      alert("Account created.");
      return { success: true };
    } else {
      const message = data.message.split(";").join("\n");
      return { success: false, error: message };
    }
  } catch (err) {
    return { success: false, error: "Could not connect to the server. Try again later." };
  }
}
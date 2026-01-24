import Cookies from "js-cookie";

const TOKEN_KEY = "token";

export const getToken = () => {
  try {
    return Cookies.get(TOKEN_KEY) || null;
  } catch (e) {
    console.error("Error reading token:", e);
    return null;
  }
};

export const setToken = (accessToken) => {
  try {
    Cookies.set(TOKEN_KEY, accessToken, {
      secure: true,
      sameSite: "Strict",
    });
  } catch (e) {
    console.error("Error setting token:", e);
  }
};

export const clearToken = () => {
  Cookies.remove(TOKEN_KEY);
};

import { getToken } from "../tokenService";

export const authInterceptor = (config) => {
  const token = getToken();

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
};

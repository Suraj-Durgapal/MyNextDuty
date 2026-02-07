export const CORE_BASE_URL = import.meta.env.VITE_CORE_BASE_URL;

export const API_URLS = {
  AUTH: {
    LOGIN: "/auth/login",
    REFRESH: "/auth/refresh",
    LOGOUT: "/auth/logout",
  },
  USER: {
    SIGNUP: "/auth/register",
    PROFILE: "/auth/profile",
  },
  LOCATION: {
    USER: "/location/user",
    UPDATE: "/location/update",
    NEARBY: "/location/nearby",
  },

};

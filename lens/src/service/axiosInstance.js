import axios from "axios";
import { CORE_BASE_URL } from "./apiUrls";
import { setupInterceptors } from "./interceptors";

const api = axios.create({
  baseURL: CORE_BASE_URL,
  withCredentials: true,
});

setupInterceptors(api);

export default api;

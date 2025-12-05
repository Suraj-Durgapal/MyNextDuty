import { Root } from "../layout/Root";
import { Loader } from "../components/common/Loader";
export const PrivateRouteConfig = [
  {
    key: "Root",
    component: Root,
    path: "/",
    children: [
      {
        key: "Loader",
        path: "",
        component: Loader,
      },
    ],
  },
];

export const PublicRouteConfig = [];

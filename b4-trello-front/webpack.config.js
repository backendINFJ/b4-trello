module.exports = {
  devServer: {
    setupMiddlewares: (middlewares, devServer) => {
      // 사용자 지정 미들웨어 추가
      middlewares.push({
        name: 'y-middleware',
        path: '/api/*',
        middleware: (req, res, next) => {
          // API 요청 처리
          next();
        },
      });
      return middlewares;
    },
  },
};
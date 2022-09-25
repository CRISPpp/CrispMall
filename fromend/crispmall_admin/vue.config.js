const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
          '/api':{
                target: 'http://127.0.0.1:9000',
                ws: true, // websockets
                changeOrigin: true,  // needed for virtual hosted sites
                pathRewrite: {
                      '^/api': ''
                }
          }
    }
}
})

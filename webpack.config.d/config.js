if (config.devServer != null) {
    config.devServer.historyApiFallback = true; // SPA dev-mode. Make webpack-dev-server return index.html for every 404.
    config.devServer.port = 8082;  // Change webpack-dev-server port.
    config.devServer.proxy = [     // Proxy API-calls to avoid CORS-restrictions in dev-mode
        {
            context: ['/api'],
            target: 'http://127.0.0.1:8080',
        },
    ];
}

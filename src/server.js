//menjalankan server menggunakan Hapi
const Hapi = require('@hapi/hapi'); //menggunakan modul hapi
const routes = require('./routes');

 
const init = async () => {
    const server = Hapi.server({
        port: 5000,
        host: 'localhost',
        routes: {
            cors: {
              origin: ['*'],
            },
          },
    });

    //gunakan route configuration pada server
    server.route(routes);
 
    await server.start();
    console.log(`Server berjalan pada ${server.info.uri}`);
};
 
init();
//======================catatan========================
//membuat method http server melalui method hapi.server
//parameter dari hapi.server meliputi server options
//server options meliputi port dan host
//proses menjalankan server.start() menggunakan await

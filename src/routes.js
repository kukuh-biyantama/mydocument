//konfigurasi routing server seperti menentukan path, method, dan handler yang digunakan.
//membuat route dan path
const 
{addBookHandler, 
getAllBooksHandler,
getBooksByIdHandler,
editBookByIdHandler,
deleteBookByIdHandler} = require('./handler');
const routes = [
    {
        method: 'POST',
        path: '/books',
        handler:addBookHandler,
    },
    {
        method: 'GET',
        path: '/books',
        handler: getAllBooksHandler,
    },
    {
        method: 'GET',
        path: '/books/{id}',
        handler: getBooksByIdHandler,
      },
    {
        method: 'PUT',
        path: '/books/{id}',
        handler: editBookByIdHandler,
    },
    {
        method: 'DELETE',
        path: '/books/{id}',
        handler: deleteBookByIdHandler,
     },
];

//export
// eslint-disable-next-line no-undef
module.exports = routes;

//============catatan=============
//fungsi handler membuat pada berkas terpisah
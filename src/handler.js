//memuat fungsi fungsi handler yang digunakan pada berkas routes
//impor
const { nanoid } = require('nanoid');
const books = require('./books')

// buat fungsi handler untuk route ini
const addBookHandler = (request, h) => {
    //cara mendapatkan bodyrequest menggunakan payload
    const {
        name,
        year,
        author,
        summary,
        publisher,
        pageCount,
        readPage,
        reading,
    } = request.payload;

    //response server jika gagal menambahkan data
    if (!name) {
        // Client tidak melampirkan properti name pada request body
        const response = h
          .response({
            status: 'fail',
            message: 'Gagal menambahkan buku. Mohon isi nama buku',
          })
          .code(400);
        return response;
      }
    
      if (readPage > pageCount) {
        // Client melampirkan nilai properti readPage yang lebih besar dari nilai properti pageCount
        const response = h
          .response({
            status: 'fail',
            message:
              'Gagal menambahkan buku. readPage tidak boleh lebih besar dari pageCount',
          })
          .code(400);
        return response;
      }

    //inisialisasi yang didapatkan pada server
    const id = nanoid(16);
    const finished = pageCount === readPage; //digunakan apakah buku telah dibaca
    const insertedAt = new Date().toISOString(); //menampung tanggal dimasukkannya buku
    const updatedAt = insertedAt; //menampung tanggal diperbarui buku

    //memasukkan kedalam array book
    const newbook = {
        id,
        name,
        year,
        author,
        summary,
        publisher,
        pageCount,
        readPage,
        reading,
        finished,
        insertedAt,
        updatedAt,
    }

    //push kedalam books array
    books.push(newbook);

    //respons is success yang diberikan server
    const isSuccess = books.filter((note) => note.id === id).length > 0;

    //gunakan isSuccess untuk menentukan respons jika berhasil
    if (isSuccess){
        const response = h.response({
            status: 'success',
            message: 'Buku berhasil ditambahkan',
            data: {
              bookId: id,
            },
        })
        //keterangan response
        .code(201);
        return response
    }

    //response gagal 500
    const respons = h.respons({
        status : "error",
        message : "Buku gagal ditambahkan"
    }).code(500);
    return respons
   
};
//mengambil nilai dari buku
const getAllBooksHandler = () => ({
  status: 'success',
  data: {
    books: books.map((book) => ({
      id: book.id,
      name: book.name,
      publisher: book.publisher,
    })),
  },
});

//menampilkan detail buku menggunakan id
const getBooksByIdHandler = (request, h) => {
  //request id
  const { id } = request.params;
  //Manfaatkan method array filter() untuk mendapatkan objeknya
  const book = books.filter((n) => n.id === id)[0];

  if (book) {
    // Bila buku dengan id yang dilampirkan ditemukan
    const response = h
      .response({
        status: 'success',
        data: {
          book,
        },
      })
      .code(200);
    return response;
  }
  const response = h.response({
    status: 'fail',
    message: 'Buku tidak ditemukan',
  });
  response.code(404);
  return response;
};

//update buku dengan completed data
const editBookByIdHandler = (request, h) => {
  const { id } = request.params;
  //request body
  const {
    name,
    year,
    author,
    summary,
    publisher,
    pageCount,
    readPage,
    reading,
} = request.payload;

if (!name) {
  // Client tidak melampirkan properti name pada request body
  const response = h
    .response({
      status: 'fail',
      message: "Gagal memperbarui buku. Mohon isi nama buku"
    })
    .code(400);
  return response;
}

if (readPage > pageCount) {
  // Client melampirkan nilai properti readPage yang lebih besar dari nilai properti pageCount
  const response = h
    .response({
      status: 'fail',
      message:'Gagal memperbarui buku. readPage tidak boleh lebih besar dari pageCount',
    })
    .code(400);
  return response;
}

const finished = pageCount === readPage; //digunakan apakah buku telah dibaca
const insertedAt = new Date().toISOString(); //menampung tanggal dimasukkannya buku
const updatedAt = insertedAt; //menampung tanggal diperbarui buku

//index akan bernilai array index dari objek catatan yang dicari.
const index = books.findIndex((note) => note.id === id);

if (index !== -1) {
  books[index] = {
    ...books[index],
    name,
    year,
    author,
    summary,
    publisher,
    pageCount,
    readPage,
    reading,
    finished,
    insertedAt,
    updatedAt,
  };
  const response = h.response({
    status: 'success',
    message: 'Buku berhasil diperbarui',
  });
  response.code(200);
  return response;
}
const response = h.response({
  status: 'fail',
  message: "Gagal memperbarui buku. Id tidak ditemukan",
});
response.code(404);
return response;
};

//delete buku
const deleteBookByIdHandler = (request, h) => {
  const { id } = request.params;

  const index = books.findIndex((note) => note.id === id);

  if (index !== -1) {
    books.splice(index, 1);
    const response = h.response({
      status: 'success',
      message: 'Buku berhasil dihapus',
    });
    response.code(200);
    return response;
  }
 
 const response = h.response({
    status: 'fail',
    message: 'Buku gagal dihapus. Id tidak ditemukan',
  });
  response.code(404);
  return response;
};

module.exports = 
{addBookHandler, 
getAllBooksHandler,
getBooksByIdHandler,
editBookByIdHandler,
deleteBookByIdHandler}
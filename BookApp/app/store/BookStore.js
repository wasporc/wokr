Ext.define('BookApp.store.BookStore', {
    extend: 'Ext.data.Store',
    model: 'BookApp.model.Book',

    storeId: 'BookStore',
    proxy: {
        type: 'ajax',
        method: 'GET',
        url: 'http://localhost:8000/api/books/',
        reader: {
            type: 'json',
            rootProperty: 'books',
            successProperty: 'success'
        }
    }
});
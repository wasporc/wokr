Ext.define('BookApp.view.BookList' ,{
    extend: 'Ext.grid.Panel',
    alias: 'widget.booklist',
    autoLoad: true,
    title: 'Библиотека',
    store: 'BookStore',
     
    initComponent: function() {
        this.columns = [
            {header: 'Название',  dataIndex: 'name',  flex: 1},
            {header: 'Автор',  dataIndex: 'author',  flex: 1},
            {header: 'Год', dataIndex: 'year', flex: 1},
            {header: 'Шкаф и полка', dataIndex: 'place', flex: 1},
            {header: 'Обложка', dataIndex: 'img', flex: 1,
                    renderer: function(value) {
                        var arr = base64ToArray(value);
                        var link = saveByte(arr);
                        return Ext.String.format('<img type="image" src={0} />', link);
                    }
            }
        ];

        this.callParent(arguments);
    }
});

function base64ToArray(base64){
     var binaryString = window.atob(base64);
    var binaryLen = binaryString.length;
    var bytes = new Uint8Array(binaryLen);
    for(var i = 0; i < binaryLen; i++){
        var ascii = binaryString.charCodeAt(i);
        bytes[i] = ascii;
    }
    return bytes;
}

function saveByte(byte){
    var blob = new Blob([byte]);
    var link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    return link;
}
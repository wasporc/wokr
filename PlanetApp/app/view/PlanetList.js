Ext.define('PlanetApp.view.PlanetList' ,{
    extend: 'Ext.grid.Panel',
    alias: 'widget.planetlist',
    autoLoad: true,
    title: 'Планетарий',
    store: 'PlanetStore',
     
    initComponent: function() {
        this.columns = [
            {header: 'Название',  dataIndex: 'name',  flex: 1},
            {header: 'История',  dataIndex: 'history',  flex: 1},
            {header: 'Радиус', dataIndex: 'radius', flex: 1},
            {header: 'Фото', dataIndex: 'photo', flex: 1,
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
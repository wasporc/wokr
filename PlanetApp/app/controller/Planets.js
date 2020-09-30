Ext.define('PlanetApp.controller.Planets', {
    extend: 'Ext.app.Controller',
 
    views: ['PlanetList', 'Planet'],
    stores: ['PlanetStore'],
    models: ['Planet'],
    init: function() {
        this.control({
            'viewport > planetlist': {
                itemdblclick: this.editPlanet
            },
            'planetwindow button[action=new]': {
                click: this.createPlanet
            },
            'planetwindow button[action=save]': {
                click: this.updatePlanet
            },
            'planetwindow button[action=delete]': {
                click: this.deletePlanet
            },
            'planetwindow button[action=clear]': {
                click: this.clearForm
            }
        });
    },
    // обновление
    updatePlanet: function(button) {
        var win    = button.up('window'),
            form   = win.down('form'),
            values = form.getValues(),
            id = form.getRecord().get('id');
            values.id=id;
        Ext.Ajax.request({
            url: 'http://localhost:8000/api/planets/',
            method: 'PUT',
            params: values,
            success: function(response){
                var data=Ext.decode(response.responseText);
                if(data.success){
                    var store = Ext.widget('planetlist').getStore();
                    store.load();
                    Ext.Msg.alert('Обновление',data.message);
                }
                else{
                    Ext.Msg.alert('Обновление','Не удалось обновить книгу в библиотеке');
                }
            }
        });
    },
    // создание
    createPlanet: function(button) {
        var win    = button.up('window'),
            form   = win.down('form'),
            values = form.getValues();
            console.log(values);
        Ext.Ajax.request({
            url: 'http://localhost:8000/api/planets/',
            method: 'POST',
            params: values,
            success: function(response, options){
                console.log(response.responseText);
                var data=Ext.decode(response.responseText);
                console.log(data);
                if(data.success){
                    Ext.Msg.alert('Создание',data.message);
                    var store = Ext.widget('planetlist').getStore();
                    store.load();
                }
                else{
                    Ext.Msg.alert('Создание','Не удалось добавить книгу в библиотеку');
                }
            }
        });
    },
    // удаление
    deletePlanet: function(button) {
        var win    = button.up('window'),
            form   = win.down('form'),
            id = form.getRecord().get('id');
        Ext.Ajax.request({
            url: 'http://localhost:8000/api/planets/',
            method: 'DELETE',
            params: {id:id},
            success: function(response){
                var data=Ext.decode(response.responseText);
                if(data.success){
                    Ext.Msg.alert('Удаление',data.message);
                    var store = Ext.widget('planetlist').getStore();
                    var record = store.getById(id);
                    store.remove(record);
                    form.getForm.reset();
                }
                else{
                    Ext.Msg.alert('Удаление','Не удалось удалить книгу из библиотеки');
                }
            }
        });
    },
    clearForm: function(grid, record) {
        var view = Ext.widget('planetwindow');
        view.down('form').getForm().reset();
    },
    editPlanet: function(grid, record) {
        var view = Ext.widget('planetwindow');
        view.down('form').loadRecord(record);
    }
});
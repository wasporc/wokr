Ext.define('PlanetApp.store.PlanetStore', {
    extend: 'Ext.data.Store',
    model: 'PlanetApp.model.Planet',

    storeId: 'PlanetStore',
    proxy: {
        type: 'ajax',
        method: 'GET',
        url: 'http://localhost:8000/api/planets/',
        reader: {
            type: 'json',
            rootProperty: 'planets',
            successProperty: 'success'
        }
    }
});
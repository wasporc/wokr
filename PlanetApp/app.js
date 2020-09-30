Ext.application({
    requires: ['Ext.container.Viewport'],
    name: 'PlanetApp',
    appFolder: 'app',
    controllers: ['Planets'],
     
    launch: function() {
        Ext.create('Ext.container.Viewport', {
            items: [{
                xtype: 'panel',
                renderTo: Ext.getBody(),
                items: {
                        xtype: 'button',
                        text: 'Создать',
                        listeners: {
                            click: function(){
                                var view = Ext.widget('planetwindow');
                                view.down('form').getForm().reset();
                            }
                        }
                    }
            },{
                xtype: 'planetlist'
            }]
        });
    }
});

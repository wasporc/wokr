Ext.application({
    requires: ['Ext.container.Viewport'],
    name: 'BookApp',
    appFolder: 'app',
    controllers: ['Books'],
     
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
                                var view = Ext.widget('bookwindow');
                                view.down('form').getForm().reset();
                            }
                        }
                    }
            },{
                xtype: 'booklist'
            }]
        });        
    }
});

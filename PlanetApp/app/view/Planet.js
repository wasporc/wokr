Ext.define('PlanetApp.view.Planet', {
    extend: 'Ext.window.Window',
    alias: 'widget.planetwindow',
 
    title: 'Планета',
    layout: 'fit',
    autoShow: true,
 
    initComponent: function() {
        this.items = [{
                xtype: 'form',
                items: [{
                        xtype: 'textfield',
                        name : 'name',
                        fieldLabel: 'Название'
                    },{
                        xtype: 'textfield',
                        name : 'history',
                        fieldLabel: 'История'
                    },{
                        xtype: 'textfield',
                        name : 'radius',
                        fieldLabel: 'Радиус'
                    },{
                        xtype: 'textfield',
                        name : 'photo',
                        id: 'photo',
                        hidden: true,
                        fieldLabel: 'images'
                    },{
                        xtype: 'fileuploadfield',
                        name : 'upload',
                        id: 'upload',
                        fieldLabel: 'Обложка',
                        msgTarget: 'none' ,
                        allowBlank: false ,
                        buttonText: 'Выбрать',
                        listeners: {
                            'change': function(fileUploadComponent, value, eOpts) {
                            this.onFileChange(fileUploadComponent, value, eOpts);
                            }
                        },
                        onFileChange: function(fileUploadComponent, value, eOpts) {
                            var file = Ext.getCmp('upload').getEl().down('input[type=file]').dom.files[0];
                            var comp = Ext.getCmp('upload').getEl().component.ariaEl.dom;
                            comp.value = file.name;
                            if (file != null) {
                                  var reader = new FileReader();
                                  reader.readAsArrayBuffer(file);
                                  reader.onloadend = function(oFREvent) {
                                      var byteArray= new Uint8Array(oFREvent.target.result);
                                      var len = byteArray.byteLength;
                                      var binary = '';
                                      for (var i = 0; i < len; i++) {
                                          binary += String.fromCharCode(byteArray[i]);
                                      }
                                       byteArray= window.btoa(binary);
                                       reader.result = byteArray;
                                       var dom = Ext.getCmp('photo').getEl().component.ariaEl.dom;
                                       dom.value = byteArray;
                                  }
                            }
                        }
                    }]
                }];
        this.dockedItems=[{
            xtype:'toolbar',
            docked: 'top',
            items: [{
                text:'Создать',
                iconCls:'new-icon',
                action: 'new'
            },{
                text:'Сохранить',
                iconCls:'save-icon',
                action: 'save'
            },{
                text:'Удалить',
                iconCls:'delete-icon',
                action: 'delete'
            }]
        }];
        this.buttons = [{
                text: 'Очистить',
                scope: this,
                action: 'clear'
        }];
 
        this.callParent(arguments);
    }
});
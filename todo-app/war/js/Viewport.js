todo.ui.Viewport = Ext.extend(Ext.Panel, {
	fullscreen : true,
	layout : 'fit',

	initComponent : function() {
		Ext.apply(this, {
			dockedItems : [ new todo.ui.Titlebar() ],
			items : [ {
				id: 'todoItems',
				xtype : 'todoitems'
			} ]
		});
		todo.ui.Viewport.superclass.initComponent.apply(this, arguments);
	}
});
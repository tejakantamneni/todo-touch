//Register model for TodoItems
Ext.regModel('TodoItem', {
    fields: ['todoText', 'dueDate']
});


Ext.setup({
	icon: 'icon.png',
    glossOnIcon: false,
    tabletStartupScreen: 'tablet_startup.png',
    phoneStartupScreen: 'phone_startup.png',
    onReady: function() {
    	var buttonsSpec = [ { ui: 'confirm', text: 'Add' } ];
    	var overlay = new Ext.Panel({
            floating: true,
            modal: true,
            centered: false,
            width: Ext.is.Phone ? 260 : 460,
            styleHtmlContent: true,
            scroll: 'vertical',
            contentEl: 'addItemForm',
            cls: 'htmlcontent'
        });
    	var showOverlay = function(btn, event) {
    		var v = Ext.get('addItemForm').show();
    	    overlay.setCentered(false);
    	    overlay.showBy(btn);
    	};
    	var showCenteredOverlay = function(btn, event) {
    	    overlay.setCentered(true);
    	    overlay.show();
    	};
    	var tapHandler = function (btn, evt) {
    	    //alert("Button '" + btn.text + "' tapped.");
    		showOverlay(btn, evt);
    	}
    	var j_store = new Ext.data.Store({
    	    model  : 'TodoItem',
    	    sorters: 'todoText',
    	    getGroupString : function(record) {
    	        return record.get('todoText')[0];
    	    },
    	    proxy: {
    	        type: 'ajax',
    	        url: '/GetAllItems',
    	        reader: {
    	            type: 'json',
    	        }
    	    },
    	    autoLoad:true
    	});

    	var dockedItems = [{
    	    xtype: 'toolbar',
    	    title: 'Todo Manager',
    	    ui: 'dark',
    	    dock: 'top',
    	    items: buttonsSpec,
    	    defaults: { handler: tapHandler }
    	}];

    	// The data list
    	var list = new Ext.List({
    	    fullscreen: true,
    	    itemTpl : '{todoText} {dueDate}',
    	    grouped : true,
    	    indexBar: true,
    	    store: j_store
    	});

    	new Ext.Application({
    	        launch: function() {
    	        	list.show();
    	        	// Main Panel
    	        	var myPanel = new Ext.Panel({
    	        		fullscreen: true,
    	        		style: 'background-color:#E3E4FA;',
    	        		items: [list],
    	        	    dockedItems: dockedItems,
    	        	});
    	        }
    	    });
    	}
});
Ext.onReady(function(){
	var form = new Ext.form.FormPanel({
		renderTo: 'addItemForm',
        width: Ext.is.Phone ? 260 : 400,
		labelWidth:60,
		id:'newItemForm',
	    items: [
	        {
	            xtype: 'textareafield',
	            name : 'details',
	            label: 'Details'
	        },
	        {
	            xtype: 'datepickerfield',
	            name : 'dueDate',
	            label: 'Due On',
	            picker: {yearFrom: 2011}
	        },{
	        	xtype: 'button', flex: 1, 
	        	text: 'Save'
	        }
	    ]
	});
	
	Ext.get('addItemForm').hide()
});
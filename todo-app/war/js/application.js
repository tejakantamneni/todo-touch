//Register model for TodoItems
Ext.regModel('TodoItem', {
    fields: ['todoText', 'dueDate']
});


Ext.setup({
	icon: 'icon.png',
    glossOnIcon: false,
    tabletStartupScreen: 'tablet_startup.png',
    phoneStartupScreen: 'phone_startup.png',
    fullscreen: true,
    onReady: function() {
    	var currentDay = new Date().format('d');
        var currentMonth = new Date().format('m');
        var currentYear = new Date().format('Y');
        
        //alert(currentDay + " - " + currentMonth + " - " + currentYear)

    	var buttonsSpec = [ { ui: 'confirm', text: 'Add' } ];
    	var overlay = new Ext.Panel({
            floating: true,
            modal: false,
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
    	    itemTpl : '{todoText} {dueDate}',
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
    	        	    dockedItems: [{
    	                    xtype: "toolbar",
    	                    title: 'Todo Manager',
    	                    dock: "top",
    	                    items: [
    	                      {
    	                        iconMask: true,
    	                        ui: "plain",
    	                        iconCls: "add",
    	                        handler: tapHandler
    	                      }
    	                    ]
    	                  }],
    	        	});
    	        }
    	    });
    	
    	var form = new Ext.form.FormPanel({
    		title: 'Create New Item',
    		frame: true,
    		url: '/CreateItem',
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
    	            picker: {yearFrom: 2011},//alert(currentDay + " - " + currentMonth + " - " + currentYear)
    	            value:{
    	            	day: currentDay,
    	            	month: currentMonth,
    	                year: currentYear
    	            }
    	        },{
    	        	xtype: 'button', flex: 1, 
    	        	text: 'Save',
    	        	handler: function(btn){
    	        		var frm  = Ext.getCmp('newItemForm');
    	        		frm.submit({
    	        			url: '/CreateItem',
    	        			method: 'POST',
    	        			success: function(){
    	        				overlay.hide();
    	        				j_store.load();
    	        			},
    	        			failure: function(){
    	        				alert('failed')
    	        			}
    	        		});
    	        	}
    	        }
    	    ]
    	});
    	form.render('addItemForm');
    	Ext.get('addItemForm').hide()
    	}
});
Ext.onReady(function(){
	var datetimePicker = new Ext.ux.touch.DateTimePicker({
	    useTitles: true,
	    id: 'dt',
	    value: {
	        day: 23,
	        month: 2,
	        year: 2000,
	        hour: 13,
	        minute: 45
	    },
	    listeners: {
	        "hide": function(picker) {
	            window.alert((picker.getValue()));
	        }
	    }
	});

});
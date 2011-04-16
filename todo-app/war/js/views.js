Ext.ns("todo", "todo.ui", "todo.model");

var currentDay = new Date().format('d');
var currentMonth = new Date().format('m');
var currentYear = new Date().format('Y');

var showCreateForm = function(btn, event) {
	var overlay = new todo.ui.CreateForm();
	overlay.showBy(btn);
};

var reloadTodoItems = function(){
	  todoItemStore.load();
};

todo.ui.Titlebar = Ext.extend(Ext.Toolbar, {
	id : 'app-titlebar',
	dock : 'top',
	title : 'Todo Items',
	items: [{
        iconMask: true,
        ui: "plain",
        iconCls: "add",
        handler: showCreateForm
      },{
          iconMask: true,
          ui: "plain",
          iconCls: "refresh",
          handler: reloadTodoItems
        }]
});

todo.ui.CreateForm = Ext.extend( Ext.form.FormPanel,{
	title: 'Create New Todo Item',
	frame: true,
	loadmask: true,
	floating: true,
    modal: true,
	url: '/CreateItem',
    width: Ext.is.Phone ? 260 : 400,
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
            picker: {yearFrom: 2011},// alert(currentDay + " - " +
										// currentMonth + " - " + currentYear)
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
        			success: reloadTodoItems,
        			failure: function(){
        				alert('failed')
        			}
        		});
        	}
        }
    ]
});

todo.model.TodoItem = Ext.regModel("todo.model.TodoItem", {
	fields : [ 'todoText', 'dueDate' ]
});

todo.ui.TodoItemList = Ext.extend(Ext.List, {
	itemTpl : '{todoText} {dueDate}',
	itemCls : 'todoItem',
	listeners : {
		itemtap : function(list, index, element, event) {
			console.log('selected: [' + typeof list.getStore().getAt(index).get('dueDate') + ']')
		}
	}
});

var todoItemStore = new Ext.data.Store({
    model  : 'todo.model.TodoItem',
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

todo.ui.TodoItems = Ext.extend(Ext.Panel, {
	fullscreen : true,
	title : 'Todo Items',
	items : [ new todo.ui.TodoItemList({
		store: todoItemStore
	}) ],
	initComponent : function() {
		todo.ui.TodoItems.superclass.initComponent.apply(this, arguments);
	},
	onRender : function() {
		todo.ui.TodoItems.superclass.onRender.apply(this, arguments);
	}
});

Ext.reg('todoitems', todo.ui.TodoItems)

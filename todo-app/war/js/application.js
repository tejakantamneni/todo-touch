//Register model for TodoItems
Ext.regModel('TodoItem', {
    fields: ['todoText', 'dueDate']
});

// A store for the todo items to display. TODO:Make data dynamic from db
// backend.
var store = new Ext.data.JsonStore({
    model  : 'TodoItem',
    sorters: 'todoText',

    getGroupString : function(record) {
        return record.get('todoText')[0];
    },

    data: [
        {todoText: 'Finish Sencha',   dueDate: 'Study and finish sample app using Sencha'},
        {todoText: 'Learn Google AppEngine',   dueDate: 'Study AppEngine and JDO store'},
        {todoText: 'Learn HTML 5',     dueDate: 'Learn HTML 5 n study SqlLite and Location Services'}
    ]
});

var j_store = new Ext.data.Store({
    model  : 'TodoItem',
    sorters: 'todoText',

    getGroupString : function(record) {
        return record.get('todoText')[0];
    },

    proxy: {
        type: 'ajax',
        url: 'allitems.json',
        reader: {
            type: 'json',
            root : 'items'
        }
    },
    autoLoad:true
});
// The data list
var list = new Ext.List({
    fullscreen: true,
    itemTpl : '{todoText} {dueDate}',
    grouped : true,
    indexBar: true,
    store: j_store
});

// Toolbar
var myToolbar = new Ext.Toolbar({
    dock : 'top',
    title: 'To-Do',
    items: [
        { text: 'New'}
    ]
});

new Ext.Application({
        launch: function() {
        	list.show();
        	// Main Panel
        	var myPanel = new Ext.Panel({
        		fullscreen: true,
        		style: 'background-color:#E3E4FA;',
        		items: [list],
        	    dockedItems: [myToolbar],
        	});
        }
    });
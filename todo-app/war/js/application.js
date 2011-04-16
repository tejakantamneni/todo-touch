Ext.setup({
    tabletStartupScreen: 'assets/images/tablet_startup.png',
    phoneStartupScreen: 'assets/images/phone_startup.png',
    icon: 'assets/images/icon.png',
    glossOnIcon: false,
    onReady: function() {
		var tp = new todo.ui.Viewport();
    }
});
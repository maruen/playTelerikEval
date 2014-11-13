var $menu = $(".dropdown-menu");

$menu.menuAim({
    activate: activateSubmenu,
    deactivate: deactivateSubmenu
});

function activateSubmenu(row) {
    var $row = $(row),
        submenuId = $row.data("submenuId"),
        $submenu = $("#" + submenuId),
        height = $menu.outerHeight(),
        width = $menu.outerWidth();


    $submenu.css({
        display: "block",
        top: -1,
        left: width - 3,  // main should overlay submenu
        height: height - 4  // padding for main dropdown's arrow
    });

    // Keep the currently activated row's highlighted look
    $row.find("a").addClass("maintainHover");
}

function deactivateSubmenu(row) {
    var $row = $(row),
        submenuId = $row.data("submenuId"),
        $submenu = $("#" + submenuId);

    // Hide the submenu and remove the row's highlighted look
    $submenu.css("display", "none");
    $row.find("a").removeClass("maintainHover");
}

// Bootstrap's dropdown menus immediately close on document click.
// Don't let this event close the menu if a submenu is being clicked.
// This event propagation control doesn't belong in the menu-aim plugin
// itself because the plugin is agnostic to bootstrap.
$(".dropdown-menu li").click(function(e) {
    e.stopPropagation();
});

$(document).click(function() {
    // Simply hide the submenu on any click. Again, this is just a hacked
    // together menu/submenu structure to show the use of jQuery-menu-aim.
    $(".popover").css("display", "none");
    $("a.maintainHover").removeClass("maintainHover");
});
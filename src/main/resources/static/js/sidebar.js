document.addEventListener('DOMContentLoaded', function() {
    const sidebarButton = document.querySelector('.sidebar-button');
    const sidebar = document.querySelector('.sidebar');

    // Function to show the sidebar with animation
    function showSidebar() {
        sidebar.style.transform = 'translateX(0)';
        sidebar.style.transition = 'transform 0.5s ease'; // Smooth transition over 0.5 seconds
    }

    // Function to hide the sidebar with animation
    function hideSidebar() {
        sidebar.style.transform = 'translateX(-100%)';
        sidebar.style.transition = 'transform 0.5s ease'; // Smooth transition over 0.5 seconds
    }

    // Event listener to show the sidebar when sidebar button is clicked
    sidebarButton.addEventListener('click', function(event) {
        showSidebar();
        event.stopPropagation(); // Prevents the click event from reaching the document
    });

    // Event listener to hide the sidebar when clicking outside of it
    document.addEventListener('click', function(event) {
        const isClickedInsideSidebar = sidebar.contains(event.target);
        if (!isClickedInsideSidebar) {
            hideSidebar();
        }
    });
});

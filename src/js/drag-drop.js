window.onload = function() {

  var dropZoneOne = document.querySelector('#drop-target-one');
  var dragElements = document.querySelectorAll('#drag-elements li');
  var elementDragged = null;

  for (var i = 0; i < dragElements.length; i++) {

    // Event Listener for when the drag interaction starts.
    dragElements[i].addEventListener('dragstart', function(e) {
      e.dataTransfer.effectAllowed = 'move';
      e.dataTransfer.setData('text', this.innerHTML);
      elementDragged = this;
    });

    // Event Listener for when the drag interaction finishes.
    dragElements[i].addEventListener('dragend', function(e) {
      elementDragged = null;
    });
  }

  // Event Listener for when the dragged element is over the drop zone.
  dropZoneOne.addEventListener('dragover', function(e) {
    if (e.preventDefault) {
      e.preventDefault();
    }

    e.dataTransfer.dropEffect = 'move';

    return false;
  });

  // Event Listener for when the dragged element enters the drop zone.
  dropZoneOne.addEventListener('dragenter', function(e) {
    this.className = "over";
  });

  // Event Listener for when the dragged element leaves the drop zone.
  dropZoneOne.addEventListener('dragleave', function(e) {
    this.className = "";
  });

  // Event Listener for when the dragged element dropped in the drop zone.
  dropZoneOne.addEventListener('drop', function(e) {
    if (e.preventDefault) e.preventDefault();
    if (e.stopPropagation) e.stopPropagation();

    this.className = "";
    this.innerHTML = "Dropped " + e.dataTransfer.getData('text');

    elementDragged = null;

    return false;
  });
};

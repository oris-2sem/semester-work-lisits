document.addEventListener('DOMContentLoaded', function () {
    var forms = document.querySelectorAll('.quantity-form');

    forms.forEach(function (form) {
        var minusBtn = form.querySelector('.minus');
        var plusBtn = form.querySelector('.plus');
        var quantityInput = form.querySelector('input[name="quantity"]');

        minusBtn.addEventListener('click', function () {
            var currentQuantity = parseInt(quantityInput.value);
            if (currentQuantity > 1) {
                quantityInput.value = currentQuantity - 1;
            }
        });

        plusBtn.addEventListener('click', function () {
            var currentQuantity = parseInt(quantityInput.value);
            quantityInput.value = currentQuantity + 1;
        });
    });
});


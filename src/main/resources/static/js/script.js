/**
 * Gerencia o comportamento dos labels flutuantes
 */
(function() {
    // Seleciona todos os inputs dentro de form-group
    const floatInputs = document.querySelectorAll('.form-group__input');

    // Aplica comportamentos a cada input
    floatInputs.forEach(input => {
        const label = input.nextElementSibling.nextElementSibling;

        // Verifica se o campo já tem valor ao carregar a página
        if (input.value.trim() !== '') {
            label.classList.add('form-group__label--active');
        }

        // Quando o campo recebe foco
        input.addEventListener('focus', () => {
            label.classList.add('form-group__label--active');
        });

        // Quando o campo perde o foco
        input.addEventListener('blur', () => {
            if (input.value.trim() === '') {
                label.classList.remove('form-group__label--active');
            }
        });
    });
})();
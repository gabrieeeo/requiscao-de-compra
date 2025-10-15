// Sistema de Requisição de Compra - JavaScript (mínimo necessário)

// Exibe o modal de confirmação de exclusão com o código e define a action do formulário
function confirmarExclusao(codigo, id) {
    const codigoEl = document.getElementById('codigoRequisicao');
    const form = document.getElementById('deleteForm');
    const modal = document.getElementById('deleteModal');

    if (codigoEl) codigoEl.textContent = codigo;
    if (form) form.action = '/requisicoes/' + id + '/excluir';
    if (modal) modal.style.display = 'block';
}

// Fecha todos os modais visíveis
function fecharModal() {
    const modals = document.querySelectorAll('.modal');
    modals.forEach(modal => {
        modal.style.display = 'none';
    });
}

// Fecha o modal ao clicar fora do conteúdo
window.onclick = function(event) {
    const modals = document.querySelectorAll('.modal');
    modals.forEach(modal => {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });
}

// Exporta somente as funções utilizadas pelas páginas
window.confirmarExclusao = confirmarExclusao;
window.fecharModal = fecharModal;


// Sistema de Requisição de Compra - JavaScript

// Função para confirmar exclusão de requisição
function confirmarExclusao(codigo, id) {
    document.getElementById('codigoRequisicao').textContent = codigo;
    document.getElementById('deleteForm').action = '/requisicoes/' + id + '/excluir';
    document.getElementById('deleteModal').style.display = 'block';
}

// Função para fechar modal
function fecharModal() {
    const modals = document.querySelectorAll('.modal');
    modals.forEach(modal => {
        modal.style.display = 'none';
    });
}

// Fechar modal ao clicar fora dele
window.onclick = function(event) {
    const modals = document.querySelectorAll('.modal');
    modals.forEach(modal => {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });
}

// Função para mostrar notificações
function mostrarNotificacao(mensagem, tipo = 'info') {
    const notificacao = document.createElement('div');
    notificacao.className = `notificacao notificacao-${tipo}`;
    notificacao.innerHTML = `
        <div class="notificacao-content">
            <span class="notificacao-mensagem">${mensagem}</span>
            <button class="notificacao-fechar" onclick="fecharNotificacao(this)">&times;</button>
        </div>
    `;
    
    // Adicionar estilos se não existirem
    if (!document.querySelector('#notificacao-styles')) {
        const styles = document.createElement('style');
        styles.id = 'notificacao-styles';
        styles.textContent = `
            .notificacao {
                position: fixed;
                top: 20px;
                right: 20px;
                z-index: 10000;
                min-width: 300px;
                max-width: 500px;
                padding: 1rem;
                border-radius: 6px;
                box-shadow: 0 4px 20px rgba(0,0,0,0.3);
                animation: slideIn 0.3s ease;
            }
            .notificacao-success {
                background-color: #d4edda;
                color: #155724;
                border: 1px solid #c3e6cb;
            }
            .notificacao-error {
                background-color: #f8d7da;
                color: #721c24;
                border: 1px solid #f5c6cb;
            }
            .notificacao-warning {
                background-color: #fff3cd;
                color: #856404;
                border: 1px solid #ffeaa7;
            }
            .notificacao-info {
                background-color: #d1ecf1;
                color: #0c5460;
                border: 1px solid #bee5eb;
            }
            .notificacao-content {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            .notificacao-fechar {
                background: none;
                border: none;
                font-size: 1.2rem;
                cursor: pointer;
                margin-left: 1rem;
            }
            @keyframes slideIn {
                from {
                    transform: translateX(100%);
                    opacity: 0;
                }
                to {
                    transform: translateX(0);
                    opacity: 1;
                }
            }
        `;
        document.head.appendChild(styles);
    }
    
    document.body.appendChild(notificacao);
    
    // Remover automaticamente após 5 segundos
    setTimeout(() => {
        if (notificacao.parentNode) {
            notificacao.remove();
        }
    }, 5000);
}

function fecharNotificacao(botao) {
    botao.closest('.notificacao').remove();
}

// Função para validar formulários
function validarFormulario(formulario) {
    const camposObrigatorios = formulario.querySelectorAll('[required]');
    let valido = true;
    
    camposObrigatorios.forEach(campo => {
        if (!campo.value.trim()) {
            campo.classList.add('error');
            valido = false;
        } else {
            campo.classList.remove('error');
        }
    });
    
    return valido;
}

// Função para formatar data
function formatarData(data) {
    if (!data) return '';
    const date = new Date(data);
    return date.toLocaleDateString('pt-BR');
}

// Função para formatar moeda
function formatarMoeda(valor) {
    if (!valor) return 'R$ 0,00';
    return new Intl.NumberFormat('pt-BR', {
        style: 'currency',
        currency: 'BRL'
    }).format(valor);
}

// Função para debounce (otimizar pesquisas)
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

// Função para pesquisar requisições em tempo real
function pesquisarRequisicoes(termo) {
    if (!termo || termo.length < 2) return;
    
    // Aqui você implementaria a lógica de pesquisa AJAX
    console.log('Pesquisando por:', termo);
}

// Aplicar debounce na pesquisa
const pesquisaDebounced = debounce(pesquisarRequisicoes, 300);

// Função para exportar dados
function exportarParaExcel(tabelaId, nomeArquivo = 'requisicoes') {
    const tabela = document.getElementById(tabelaId);
    if (!tabela) return;
    
    let csv = '';
    const linhas = tabela.querySelectorAll('tr');
    
    linhas.forEach(linha => {
        const celulas = linha.querySelectorAll('td, th');
        const linhaCSV = Array.from(celulas).map(celula => {
            return '"' + celula.textContent.replace(/"/g, '""') + '"';
        }).join(',');
        csv += linhaCSV + '\n';
    });
    
    // Criar e baixar arquivo
    const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
    const link = document.createElement('a');
    const url = URL.createObjectURL(blob);
    link.setAttribute('href', url);
    link.setAttribute('download', nomeArquivo + '.csv');
    link.style.visibility = 'hidden';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}

// Função para imprimir página
function imprimirPagina() {
    window.print();
}

// Função para alternar tema (claro/escuro)
function alternarTema() {
    const body = document.body;
    const temaAtual = localStorage.getItem('tema') || 'claro';
    const novoTema = temaAtual === 'claro' ? 'escuro' : 'claro';
    
    body.classList.toggle('tema-escuro', novoTema === 'escuro');
    localStorage.setItem('tema', novoTema);
    
    mostrarNotificacao(`Tema alterado para ${novoTema}`, 'info');
}

// Aplicar tema salvo ao carregar a página
document.addEventListener('DOMContentLoaded', function() {
    const temaSalvo = localStorage.getItem('tema');
    if (temaSalvo === 'escuro') {
        document.body.classList.add('tema-escuro');
    }
});

// Função para mostrar loading
function mostrarLoading(elemento) {
    const loading = document.createElement('div');
    loading.className = 'loading';
    loading.innerHTML = `
        <div class="loading-spinner"></div>
        <div class="loading-text">Carregando...</div>
    `;
    
    // Adicionar estilos se não existirem
    if (!document.querySelector('#loading-styles')) {
        const styles = document.createElement('style');
        styles.id = 'loading-styles';
        styles.textContent = `
            .loading {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0,0,0,0.5);
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                z-index: 10000;
            }
            .loading-spinner {
                width: 50px;
                height: 50px;
                border: 5px solid #f3f3f3;
                border-top: 5px solid #3498db;
                border-radius: 50%;
                animation: spin 1s linear infinite;
            }
            .loading-text {
                color: white;
                margin-top: 1rem;
                font-size: 1.1rem;
            }
            @keyframes spin {
                0% { transform: rotate(0deg); }
                100% { transform: rotate(360deg); }
            }
        `;
        document.head.appendChild(styles);
    }
    
    document.body.appendChild(loading);
    return loading;
}

function esconderLoading(loading) {
    if (loading && loading.parentNode) {
        loading.remove();
    }
}

// Função para confirmar ações importantes
function confirmarAcao(mensagem, callback) {
    if (confirm(mensagem)) {
        callback();
    }
}

// Função para copiar texto para área de transferência
function copiarParaAreaTransferencia(texto) {
    navigator.clipboard.writeText(texto).then(() => {
        mostrarNotificacao('Texto copiado para a área de transferência!', 'success');
    }).catch(() => {
        mostrarNotificacao('Erro ao copiar texto', 'error');
    });
}

// Função para validar CPF (se necessário)
function validarCPF(cpf) {
    cpf = cpf.replace(/[^\d]/g, '');
    
    if (cpf.length !== 11) return false;
    
    // Verificar se todos os dígitos são iguais
    if (/^(\d)\1{10}$/.test(cpf)) return false;
    
    // Validar dígitos verificadores
    let soma = 0;
    for (let i = 0; i < 9; i++) {
        soma += parseInt(cpf.charAt(i)) * (10 - i);
    }
    let resto = 11 - (soma % 11);
    if (resto === 10 || resto === 11) resto = 0;
    if (resto !== parseInt(cpf.charAt(9))) return false;
    
    soma = 0;
    for (let i = 0; i < 10; i++) {
        soma += parseInt(cpf.charAt(i)) * (11 - i);
    }
    resto = 11 - (soma % 11);
    if (resto === 10 || resto === 11) resto = 0;
    if (resto !== parseInt(cpf.charAt(10))) return false;
    
    return true;
}

// Função para validar CNPJ (se necessário)
function validarCNPJ(cnpj) {
    cnpj = cnpj.replace(/[^\d]/g, '');
    
    if (cnpj.length !== 14) return false;
    
    // Verificar se todos os dígitos são iguais
    if (/^(\d)\1{13}$/.test(cnpj)) return false;
    
    // Validar primeiro dígito verificador
    let soma = 0;
    let peso = 2;
    for (let i = 11; i >= 0; i--) {
        soma += parseInt(cnpj.charAt(i)) * peso;
        peso = peso === 9 ? 2 : peso + 1;
    }
    let resto = soma % 11;
    let dv1 = resto < 2 ? 0 : 11 - resto;
    if (dv1 !== parseInt(cnpj.charAt(12))) return false;
    
    // Validar segundo dígito verificador
    soma = 0;
    peso = 2;
    for (let i = 12; i >= 0; i--) {
        soma += parseInt(cnpj.charAt(i)) * peso;
        peso = peso === 9 ? 2 : peso + 1;
    }
    resto = soma % 11;
    let dv2 = resto < 2 ? 0 : 11 - resto;
    if (dv2 !== parseInt(cnpj.charAt(13))) return false;
    
    return true;
}

// Função para formatar telefone
function formatarTelefone(telefone) {
    const numeros = telefone.replace(/\D/g, '');
    
    if (numeros.length === 11) {
        return numeros.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3');
    } else if (numeros.length === 10) {
        return numeros.replace(/(\d{2})(\d{4})(\d{4})/, '($1) $2-$3');
    }
    
    return telefone;
}

// Função para formatar CEP
function formatarCEP(cep) {
    const numeros = cep.replace(/\D/g, '');
    return numeros.replace(/(\d{5})(\d{3})/, '$1-$2');
}

// Event listeners globais
document.addEventListener('DOMContentLoaded', function() {
    // Adicionar máscaras aos campos
    const camposTelefone = document.querySelectorAll('input[type="tel"]');
    camposTelefone.forEach(campo => {
        campo.addEventListener('input', function(e) {
            e.target.value = formatarTelefone(e.target.value);
        });
    });
    
    const camposCEP = document.querySelectorAll('input[name*="cep"]');
    camposCEP.forEach(campo => {
        campo.addEventListener('input', function(e) {
            e.target.value = formatarCEP(e.target.value);
        });
    });
    
    // Adicionar validação em tempo real
    const camposObrigatorios = document.querySelectorAll('[required]');
    camposObrigatorios.forEach(campo => {
        campo.addEventListener('blur', function() {
            if (!this.value.trim()) {
                this.classList.add('error');
            } else {
                this.classList.remove('error');
            }
        });
    });
    
    // Adicionar estilos para campos com erro
    if (!document.querySelector('#error-styles')) {
        const styles = document.createElement('style');
        styles.id = 'error-styles';
        styles.textContent = `
            .error {
                border-color: #e74c3c !important;
                box-shadow: 0 0 0 3px rgba(231, 76, 60, 0.1) !important;
            }
        `;
        document.head.appendChild(styles);
    }
});

// Função para mostrar modal de confirmação personalizado
function mostrarModalConfirmacao(titulo, mensagem, callbackConfirmar, callbackCancelar) {
    const modal = document.createElement('div');
    modal.className = 'modal';
    modal.style.display = 'block';
    modal.innerHTML = `
        <div class="modal-content">
            <div class="modal-header">
                <h3>${titulo}</h3>
                <span class="close" onclick="fecharModalPersonalizado(this)">&times;</span>
            </div>
            <div class="modal-body">
                <p>${mensagem}</p>
            </div>
            <div class="modal-footer">
                <button class="btn btn-secondary" onclick="fecharModalPersonalizado(this)">Cancelar</button>
                <button class="btn btn-primary" onclick="confirmarModalPersonalizado(this)">Confirmar</button>
            </div>
        </div>
    `;
    
    document.body.appendChild(modal);
    
    // Armazenar callbacks
    modal.callbackConfirmar = callbackConfirmar;
    modal.callbackCancelar = callbackCancelar;
}

function fecharModalPersonalizado(elemento) {
    const modal = elemento.closest('.modal');
    if (modal.callbackCancelar) {
        modal.callbackCancelar();
    }
    modal.remove();
}

function confirmarModalPersonalizado(elemento) {
    const modal = elemento.closest('.modal');
    if (modal.callbackConfirmar) {
        modal.callbackConfirmar();
    }
    modal.remove();
}

// Exportar funções para uso global
window.confirmarExclusao = confirmarExclusao;
window.fecharModal = fecharModal;
window.mostrarNotificacao = mostrarNotificacao;
window.validarFormulario = validarFormulario;
window.formatarData = formatarData;
window.formatarMoeda = formatarMoeda;
window.exportarParaExcel = exportarParaExcel;
window.imprimirPagina = imprimirPagina;
window.alternarTema = alternarTema;
window.mostrarLoading = mostrarLoading;
window.esconderLoading = esconderLoading;
window.confirmarAcao = confirmarAcao;
window.copiarParaAreaTransferencia = copiarParaAreaTransferencia;
window.validarCPF = validarCPF;
window.validarCNPJ = validarCNPJ;
window.formatarTelefone = formatarTelefone;
window.formatarCEP = formatarCEP;
window.mostrarModalConfirmacao = mostrarModalConfirmacao;


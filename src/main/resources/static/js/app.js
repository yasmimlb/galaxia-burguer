document.addEventListener('DOMContentLoaded', async () => {
    // Elementos do DOM
    const lanchesContainer = document.getElementById('lanches-container');
    const pedidosContainer = document.getElementById('pedidos-container');
    const pedidoForm = document.getElementById('pedido-form');
    const lancheSelect = document.getElementById('lanche-select');
    const pedidoStatus = document.getElementById('pedido-status');

    // Carregar lanches
    const loadLanches = async () => {
        lanchesContainer.innerHTML = '<div class="loading"></div> Carregando cardápio...';

        try {
            const response = await fetch('/api/lanches');
            if (!response.ok) throw new Error('Erro na rede');

            const lanches = await response.json();

            lanchesContainer.innerHTML = lanches.map(lanche => `
                <div class="lanche-card animate__animated animate__fadeIn">
                    <div class="lanche-image" style="background-image: url('images/${lanche.id}.jpg'), url('https://via.placeholder.com/300x300/0d0221/6a0dad?text=Imagem+Não+Encontrada')"></div>
                    <div class="lanche-info">
                        <h3>${lanche.nome}</h3>
                        <p>${lanche.descricao}</p>
                        <p class="preco">R$ ${lanche.preco.toFixed(2)}</p>
                    </div>
                </div>
            `).join('');

            // Preencher dropdown
            lancheSelect.innerHTML = '<option value="">-- Selecione --</option>' +
                lanches.map(lanche =>
                    `<option value="${lanche.id}">${lanche.nome} - R$ ${lanche.preco.toFixed(2)}</option>`
                ).join('');

        } catch (error) {
            lanchesContainer.innerHTML = '<p class="error">🚨 Falha ao carregar cardápio. Recarregue a página.</p>';
            console.error('Erro:', error);
        }
    };

    // Carregar pedidos
    const loadPedidos = async () => {
        pedidosContainer.innerHTML = '<div class="loading"></div> Carregando pedidos...';

        try {
            const response = await fetch('/api/pedidos');
            if (!response.ok) throw new Error('Erro na rede');

            const pedidos = await response.json();

            pedidosContainer.innerHTML = pedidos.map(pedido => `
                <div class="pedido-card">
                    <p><strong>Pedido #${pedido.id}</strong></p>
                    <p>Status: ${pedido.status}</p>
                    <p>Itens: ${pedido.lanchesIds.length}</p>
                    <p>Itens: ${pedido.nomesLanches.join(", ")}</p> <!-- AGORA vai mostrar o nome do pedido hehehehe-->
                </div>
            `).join('');

        } catch (error) {
            pedidosContainer.innerHTML = '<p class="error">🚨 Falha ao carregar pedidos</p>';
        }
    };

    // Enviar pedido
    pedidoForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const button = e.target.querySelector('button');
        button.disabled = true;
        button.textContent = 'Enviando...';

        try {
            const lancheId = parseInt(lancheSelect.value);
            if (!lancheId) throw new Error('Selecione um lanche');

            // Corpo do pedido no formato EXATO que a API espera
            const response = await fetch('http://localhost:8080/api/pedidos', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    lanchesIds: [lancheId], // Array com o ID selecionado
                    status: "RECEBIDO"      // Status fixo (como no Insomnia)
                })
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Erro no servidor');
            }

            // Sucesso!
            showRocket();
            pedidoStatus.innerHTML = '<p class="success">Pedido enviado! 🚀</p>';
            await loadPedidos(); // Atualiza a lista

        } catch (error) {
            pedidoStatus.innerHTML = `<p class="error">Erro: ${error.message}</p>`;
            console.error("Detalhes do erro:", error); // Verifique no console (F12)
        } finally {
            button.disabled = false;
            button.textContent = 'ENVIAR PARA A COZINHA ESPACIAL';
        }
    });

    // Efeito de foguete
    function showRocket() {
        const rocket = document.createElement('div');
        rocket.innerHTML = '🚀';
        rocket.style.position = 'fixed';
        rocket.style.bottom = '20px';
        rocket.style.right = '20px';
        rocket.style.fontSize = '3rem';
        rocket.style.animation = 'rocket 2s ease-out forwards';
        document.body.appendChild(rocket);

        setTimeout(() => rocket.remove(), 2000);
    }

    // Inicialização
    loadLanches();
    loadPedidos();
});
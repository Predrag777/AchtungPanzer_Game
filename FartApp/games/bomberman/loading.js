// Find where loading completes and game starts — wrap that call with:

function fadeIntoGame(startGameCallback) {
  const overlay = document.getElementById('fade-overlay');
  // Step 1: fade screen to black
  overlay.style.pointerEvents = 'all';
  overlay.style.opacity = '1';

  setTimeout(() => {
    // Step 2: hide loading screen, start game
    const loadingScreen = document.getElementById('loading-screen') 
      || document.querySelector('.loading-screen') 
      || document.querySelector('#loading');
    if (loadingScreen) loadingScreen.style.display = 'none';

    if (startGameCallback) startGameCallback();

    // Step 3: fade from black to reveal gameplay
    requestAnimationFrame(() => {
      overlay.style.opacity = '0';
      overlay.style.pointerEvents = 'none';
    });
  }, 650);
}

// Replace direct game start call, e.g.:
//   startGame() or showGame() or similar
// with:
//   fadeIntoGame(() => startGame());
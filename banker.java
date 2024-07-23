// Item IDs
int bird_nest_1 = 22798;
int bird_nest_2 = 5074; 
int bird_nest_3 = 5072; 
int bird_nest_4 = 5071; 
int bird_nest_5 = 5070;
int red_log = 19669;

// WorldPoints
WorldPoint wp1 = new WorldPoint(1592, 3476, 0);
WorldPoint wp2 = new WorldPoint(1582, 3488, 0);
WorldPoint wp3 = new WorldPoint(1574,3483,1);
WorldPoint currentLocation = client.getLocalPlayer().getWorldLocation();

// Method for grabbing bird nests
private void grabBirdNests() {
    v.getGroundItem().take(bird_nest_1);
    v.getGroundItem().take(bird_nest_2);
    v.getGroundItem().take(bird_nest_3);
    v.getGroundItem().take(bird_nest_4);
    v.getGroundItem().take(bird_nest_5);
}

// Method for handling random events
private void handleRandomEvents() {
    v.getNpc().dismissRandomEvent();
}

// Method for checking special energy and attacking if possible
private void useSpecialAttack() {
    if (v.getCombat().specRemaining(100, 100)) {
        v.getCombat().spec(1);
    }
}
    // Constantly check for bird nests, random events and special attack availability
    grabBirdNests();
    //handleRandomEvents();
    useSpecialAttack();
    
// Method for banking items when inventory is full
private void bankItems() {
    if (v.getInventory().inventoryFull() && !v.getWalking().isMoving() && v.getLocalPlayer().hasAnimation(-1)) {
        currentLocation = client.getLocalPlayer().getWorldLocation();
        if (!currentLocation.equals(wp1)) {
            v.getWalking().walk(wp1);
        } else {
            GameObject bankChest = v.getGameObject().findNearest(28861); // findNearest() function returns the nearest game object with the given ID
            if (bankChest != null) {
                int bankChestSceneX = bankChest.getSceneMinLocation().getX();
                int bankChestSceneY = bankChest.getSceneMinLocation().getY();
                v.invoke("Use","<col=ffff>Bank chest", 28861, 3, bankChestSceneX, bankChestSceneY, false);
                v.getCallbacks().afterTicks(1, () -> {
                    v.getBank().deposit(red_log ,27);
                    v.getBank().deposit(bird_nest_1, 10);
                    v.getBank().deposit(bird_nest_2, 10);
                    v.getBank().deposit(bird_nest_3, 10);
                    v.getBank().deposit(bird_nest_4, 10);
                    v.getBank().deposit(bird_nest_5, 10);
                });
            }
        }
    }
}

// Method for climbing down when inventory is full
private void manageInventory() {
    if (v.getInventory().inventoryFull()) {
        v.getWalking().walk(wp3);
        currentLocation = client.getLocalPlayer().getWorldLocation();
        if (currentLocation.equals(wp3) && !v.getWalking().isMoving() && v.getInventory().inventoryFull()) {
            GameObject ladder = v.getGameObject().findNearest(28858); // findNearest() function returns the nearest game object with the given ID
            if (ladder != null) {
                int ladderSceneX = ladder.getSceneMinLocation().getX();
                int ladderSceneY = ladder.getSceneMinLocation().getY();
                v.invoke("Climb-down", "<col=ffff>Rope ladder", 28858, 3, ladderSceneX, ladderSceneY, false);
            }
        }
    }
}

// Main loop
if (!v.getInventory().inventoryFull() && !v.getWalking().isMoving() && v.getLocalPlayer().hasAnimation(-1)) {
    currentLocation = client.getLocalPlayer().getWorldLocation();
    if (!currentLocation.equals(wp1) && !currentLocation.equals(wp2)) {
        v.getWalking().walk(wp1);
    }
    else if (currentLocation.equals(wp1) && !currentLocation.equals(wp2) && !v.getWalking().isMoving()) {
        v.getWalking().walk(wp2);
    } 
    else if (currentLocation.equals(wp2) && !v.getWalking().isMoving()) {
        GameObject ladder = v.getGameObject().findNearest(28857); // findNearest() function returns the nearest game object with the given ID
        if (ladder != null) {
            int ladderSceneX = ladder.getSceneMinLocation().getX();
            int ladderSceneY = ladder.getSceneMinLocation().getY();
            v.invoke("Climb-up", "<col=ffff>Rope ladder", 28857, 3, ladderSceneX, ladderSceneY, false);
        }
    }


    // If the player is not performing any animation, initiate woodcutting
    if (v.getLocalPlayer().hasAnimation(-1) && !v.getInventory().amountInInventory(19699, 28, 28)) {
        v.getCallbacks().afterTicks(1, () -> {
            v.getWoodcutting().chop(29670, 29668);
        });
    }
}

// Manage inventory and bank items if full
manageInventory();
bankItems();

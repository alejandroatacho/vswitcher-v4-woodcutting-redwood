// Variable for level, could be removed if not used elsewhere
int lvl = client.getBoostedSkillLevel(Skill.WOODCUTTING);
// GameObject that_hot_tree_i_wanna_touch = v.getGameObjects().findNearest(40758);



// Method for grabbing bird nests
private void grabBirdNests() {
    v.getGroundItem().take(22798); // Bird nest seeds
    v.getGroundItem().take(5074); // Bird nest rings
    v.getGroundItem().take(5072); // Bird nest blue eggs
    v.getGroundItem().take(5070); // Bird nest red eggs?
    v.getGroundItem().take(5071); // Bird nest a green eggs
}

// Method for handling random events
private void handleRandomEvents() {
    v.getNpc().dismissRandomEvent();
}
//Method calling or else it will only do them when we are idle, and we will loose bird nest
grabBirdNests();
//handleRandomEvents();

// Method for checking special energy and attacking if possible
private void useSpecialAttack() {
    if (v.getCombat().specRemaining(100, 100)) {
        v.getCombat().spec(1);
    }
}
//Method call so it always does it on 100%
useSpecialAttack();

// Method for dropping items if inventory is full
private void manageInventory() {
    if (v.getInventory().inventoryFull()) {
        v.getInventory().drop(19669); // Teak log id
    }
}

// Main loop
if (v.getLocalPlayer().hasAnimation(-1) && !v.getInventory().amountInInventory(19699, 28, 28)) {

    manageInventory();
  v.getCallbacks().afterTicks(1, () -> {
    // Cut red wood
    v.getWoodcutting().chop(29670, 29668);
           });
}
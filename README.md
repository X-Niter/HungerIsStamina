<p>
  <a href="https://discord.gg/n3uEJwg">
      <img src="https://discordapp.com/api/guilds/401537312246202389/widget.png" alt="Discord-Server"/>
  </a>
  <a href="https://twitter.com/X_Niter">
    <img alt="Twitter: X_Niter" src="https://img.shields.io/twitter/follow/X_Niter.svg?style=social" target="_blank" />
  </a>
  <a href="https://patreon.com/X_Niter">
    <img src="https://img.shields.io/endpoint.svg?url=https%3A%2F%2Fshieldsio-patreon.vercel.app%2Fapi%3Fusername%3DX_Niter%26type%3Dpatrons&style=flat" alt="Support me on Patreon" />
  </a>
  <a href="https://www.paypal.com/paypalme/RileyBWayz">
    <img src="https://img.shields.io/badge/Donate-PayPal-blue.svg">
</p>
  
# HungerIsStamina or (HIS)
An advanced stamina system addon for MMOCore
  
HungerIsStamina's only purpose was to fullfill a custom need for a full fledged Stamina system in the Minecraft game.
The Original intent was to simply make running take away stamina and sync it with the hunger bar, though the goals of that changed very quickly.

It is now a full Stamina system which too include stamina costs for Sprinting, Jumping & Swimming, simply to better integrate MMOCore into Vanilla Minecraft servers a bit more then what it is.

Here is the default configuration file, it should be pretty understanding as to what features this addon introduces.
<details>
  <summary>Config.yml</summary>
  
  ```yml
  # [[ Hunger is Stamina - v1.2.0 ]]
#   Motivated by ASangarin, Created by X_Niter


# NOTE: Any changes made here requires restart, reloading is not a feature in this addon!

# Is this Plugin Enabled [True = YES, False = NO]
IsPluginEnabled: true


####################################################################
#                     SPRINTING STAMINA                            #
####################################################################

# Do you want sprinting to drain stamina? [True = YES, False = NO]
StaminaCostForSprintingEnabled: True

# Stamina cost while sprinting, cost will apply every second the player is running
StaminaCostForSprinting: 1

# How many Ticks pass before draining the stamina cost from the players stamina
# DEFAULT is drain "StaminaCostForSprinting" every (1)second of sprinting [20 Ticks = 1 Second]
StaminaDrainTickSpeedSprint: 20



####################################################################
#                     JUMPING STAMINA                              #
####################################################################

# Do you want jumping to drain stamina? [True = YES, False = NO]
StaminaCostForJumpingEnabled: True

# Stamina Cost for when a Player jumps
StaminaCostForJumping: 2

StaminaDrainTickSpeedJump: 20

####################################################################
#                     SWIMMING STAMINA                             #
####################################################################

# Do you want swimming to drain stamina? [True = YES, False = NO]
StaminaCostForSwimmingEnabled: True

# Stamina cost while swimming, cost will apply every second the player is swimming
StaminaCostForSwimming: 1

StaminaDrainTickSpeedSwim: 20


####################################################################
#                       PLUGIN EXTRAS                              #
####################################################################

# Get notified when there is a new version available
# Only Available if "config-version" is 4+ as of right now!
Update-Checker: false

# Enable if you want to see more information about the plugins functions.
Debug-Enabled: false

# DO NOT TOUCH, IT'S AT THE BOTTOM FOR A REASON!
config-version: 4


####################################################################
#                          FOODS                                   #
####################################################################

# When food bar/stamina is empty or 0, should the player take Vanilla Minecraft Starvation Damage.
# If True then player will take Minecraft Starvation Damage.
# If False, then the player will not take Starvation damage.
StarvationDamage: true

# If Starvation Damage is true, how much damage should they take?
StarvationDamageValue: 0.5

# Enabled Hunger Bar Regeneration
# DEFAULT, True = Enabled
EnableRegen: True

# The max a player hunger will regen too.
# 1 equal to half a drumstick.
#DEFAULT, 10 = Half a Hunger
HungerRegenLimit: 10

# Amount of hunger to regen every "RegenTimeInTicks"
HungerRegenAmount: 1

# Time it takes to regen Hunger
# 20 Ticks equals 1 second
# DEFAULT, 4 seconds
RegenTimeInTicks: 80

# When player eats to fill the hunger bar, do you want food to fill a little bit of stamina.
FoodFillStaminaPartial: true

# Enables you to change food values in Foods.yml
EnableCustomFoodValues: True
  ```
 </details>
  
  [Find it on spigot!](https://www.spigotmc.org/resources/hunger-is-stamina-mmocore-addon.95964/)
  
  ## Features
  - Enable/Disable Plugin
  - Some Commands
  - Stamina Sprint System
  - Stamina Jump System
  - Stamina Swim System
  - Custom Food Values
  - Hunger Regen Control
  - & Whatever else gets suggested and added in the future!

  
  
  
  ### Future Plan
- [x] Listen for Suggestions from you amazing keyboard titans!
- [ ] Stamina Bar Placeholder, A revamp to MMOCores.

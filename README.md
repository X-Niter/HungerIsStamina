# HungerIsStamina
MMOCore Addon to turn the hunger bar into a stamina bar

In short, this turnes the hunger bar into MMOCore's Stamina Resource.
Anytime you Stamina changes your hunger bar will update accordingly.

```
One Flaw I'm going to mention to you right now! Listen up!
This will make food do NOTHING, so I highly suggest you make food give MMOCore stamina + whatever buff you want to allow the food to have!

In the future I have plans to add EVERY food to the config with Stamina values which you can change.

As for CustomModelData Foods, I'd imagine there's a way you can make those just give MMOCore Resource to player but I hope I can find a way in the code to allow adding custom model data into the config which will then make them give stamina, 
Some thing like
- COOKED_BEEF:1:2
Being Name, CustomModelData ID, and then the stamina amount!
```

Current features ---
- Stamina Cost for Sprinting
- Stamina Cost for Jumping
- Stamina Cost for Swimming


Future Plan
- [ ] Sprint until not enough stamina, currently can't sprint when hunger is < 3/4
- [ ] Cancel Jumping when not enough Stamina
- [ ] Cancel Swimming action when not enough stamina
- [ ] Food to Stamina system
- [x] Listen for Suggestions from you amazing keyboard titans!

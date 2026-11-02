import json
import googletrans
import asyncio

namesByFiles = []
files = ["ModArmorEnchantments.java", "ModToolEnchantments.java", "ModMeleeEnchantments.java", "ModRangedEnchantments.java"]
translator = googletrans.Translator()
process_line = lambda line: line.replace("public static ", "").replace("ResourceKey<Enchantment> ", "").replace("final ", "").strip().split(" = ")[0].replace("_", " ").title()
to_key = lambda name: "enchantment.endchants." + name.lower().replace(" ", "_")
for filename in files:
    with open(f"./src/main/java/com/endchants/{filename}", "r") as file:
        namesByFiles.append([process_line(line.strip()) for line in file if "ResourceKey<Enchantment>" in line and " = " in line])
names = [name for sublist in namesByFiles for name in sublist]
trans_dict = {to_key(name): name for name in names}
with open("./src/main/resources/assets/endchants/lang/en_US.json", "w") as file:
    json.dump(trans_dict, file)
async def translate():
    german_translations = {to_key(name): (await translator.translate(name, dest="de", src="en")).text for name in names}
    with open("./src/main/resources/assets/endchants/lang/de_DE.json", "w") as file:
        json.dump(german_translations, file)
    french_translations = {to_key(name): (await translator.translate(name, dest="fr", src="en")).text for name in names}
    
    with open("./src/main/resources/assets/endchants/lang/fr_FR.json", "w") as file:
        json.dump(french_translations, file)
    spanish_translations = {to_key(name): (await translator.translate(name, dest="es", src="en")).text for name in names}
    with open("./src/main/resources/assets/endchants/lang/es_ES.json", "w") as file:
        json.dump(spanish_translations, file)
asyncio.run(translate())
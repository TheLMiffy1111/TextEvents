package thelm.textevents.asm;

import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ASTORE;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.IFGT;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;
import thelm.textevents.TextEvents;

//Thanks EnderCore
public class TextEventsClassTransformer implements IClassTransformer {

	//book
	private static final String guiScreenBookClass = "net.minecraft.client.gui.GuiScreenBook";
	private static final ObfSafeName bookField = new ObfSafeName("book", "field_146474_h");
	private static final ObfSafeName drawScreenMethod = new ObfSafeName("drawScreen", "func_73863_a");
	private static final ObfSafeName getStringTagAtMethod = new ObfSafeName("getStringTagAt", "func_150307_f");
	//entityname
	private static final String renderClass = "net.minecraft.client.renderer.entity.Render";
	private static final ObfSafeName renderLivingLabelMethod = new ObfSafeName("renderLivingLabel", "func_147906_a");
	//sign
	private static final String tileEntitySignRendererClass = "net.minecraft.client.renderer.tileentity.TileEntitySignRenderer";
	private static final ObfSafeName renderMethod = new ObfSafeName("render", "func_192841_a");
	private static final ObfSafeName signTextField = new ObfSafeName("signText", "field_145915_a");
	//tilename
	//title
	private static final String netHandlerPlayClientClass = "net.minecraft.client.network.NetHandlerPlayClient";
	private static final ObfSafeName handleTitleMethod = new ObfSafeName("handleTitle", "func_175099_a");
	//bossbar
	private static final String bossInfoClientClass = "net.minecraft.client.gui.BossInfoClient";
	private static final String initMethod = "<init>";
	private static final ObfSafeName updateFromPacketMethod = new ObfSafeName("updateFromPacket", "func_186765_a");
	private static final ObfSafeName getNameMethod = new ObfSafeName("getName", "func_186907_c");
	//itemname
	private static final String itemStackClass = "net.minecraft.item.ItemStack";
	private static final ObfSafeName getDisplayNameMethodItemStack = new ObfSafeName("getDisplayName", "func_82833_r");
	private static final ObfSafeName getSubCompoundMethod = new ObfSafeName("getSubCompound", "func_179543_a");
	//playername
	private static final String networkPlayerInfoClass = "net.minecraft.client.network.NetworkPlayerInfo";
	private static final String initMethodDesc = "(Lnet/minecraft/network/play/server/SPacketPlayerListItem$AddPlayerData;)V";
	private static final ObfSafeName handlePlayerListItemMethod = new ObfSafeName("handlePlayerListItem", "func_147256_a");
	private static final ObfSafeName getDisplayNameMethodAddPlayerData = new ObfSafeName("getDisplayName", "func_179961_d");

	protected static class ObfSafeName {
		final String deobf, srg;

		public ObfSafeName(String deobf, String srg) {
			this.deobf = deobf;
			this.srg = srg;
		}

		public ObfSafeName(ObfSafeName... names) {
			StringBuilder deobf = new StringBuilder();
			StringBuilder srg = new StringBuilder();
			if(names.length > 0) {
				deobf.append(names[0].deobf);
				srg.append(names[0].srg);
			}
			for(int i = 1; i < names.length; i++) {
				deobf.append(", ");
				srg.append(", ");
				deobf.append(names[0].deobf);
				srg.append(names[0].srg);
			}
			this.deobf = deobf.toString();
			this.srg = srg.toString();
		}

		public String getName() {
			return TextEventsLoadingPlugin.runtimeDeobfEnabled ? srg : deobf;
		}

		public boolean equals(String obj) {
			if(obj != null) {
				return obj.equals(deobf) || obj.equals(srg);
			}
			return false;
		}

		@Override
		public boolean equals(Object obj) {
			if(obj instanceof String) {
				return obj.equals(deobf) || obj.equals(srg);
			}
			else if(obj instanceof ObfSafeName) {
				return ((ObfSafeName)obj).deobf.equals(deobf) && ((ObfSafeName)obj).srg.equals(srg);
			}
			return false;
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}
	}

	protected static interface Transform {
		void transform(ClassNode classNode);
	}

	protected final static byte[] transform(byte[] classBytes, String className, ObfSafeName methodName, Transform transformer) {
		TextEvents.LOGGER.info("Transforming Class [" + className + "], Method [" + methodName.getName() + "]");

		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(classBytes);
		classReader.accept(classNode, 0);

		transformer.transform(classNode);

		ClassWriter cw = new ClassWriter(0);
		classNode.accept(cw);
		TextEvents.LOGGER.info("Transforming " + className + " Finished.");
		return cw.toByteArray();
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if(guiScreenBookClass.equals(transformedName)) {
			return transform(basicClass, guiScreenBookClass, drawScreenMethod, classNode->{
				boolean done = false;
				for(FieldNode f : classNode.fields) {
					if(bookField.equals(f.name)) {
						for(MethodNode m : classNode.methods) {
							if(drawScreenMethod.equals(m.name)) {
								InsnList toAdd = new InsnList();
								toAdd.add(new VarInsnNode(ALOAD, 0));
								toAdd.add(new FieldInsnNode(GETFIELD, transformedName.replace('.', '/'), f.name, "Lnet/minecraft/item/ItemStack;"));
								toAdd.add(new MethodInsnNode(INVOKESTATIC, "thelm/textevents/events/TextEventFactory", "onRenderPageTextInBook", "(Ljava/lang/String;Lnet/minecraft/item/ItemStack;)Ljava/lang/String;", false));

								for(int i = 0; i < m.instructions.size(); i++) {
									AbstractInsnNode next = m.instructions.get(i);

									if(next.getOpcode() == INVOKEVIRTUAL && next instanceof MethodInsnNode) {
										if(getStringTagAtMethod.equals(((MethodInsnNode)next).name)) {
											m.instructions.insert(next, toAdd);
											done = true;
											break;
										}
									}
								}
								break;
							}
						}
						break;
					}
				}
				if(!done) {
					TextEvents.LOGGER.info("Transforming failed.");
				}
			});
		}
		if(renderClass.equals(transformedName)) {
			return transform(basicClass, renderClass, renderLivingLabelMethod, classNode->{
				boolean done = false;
				for(MethodNode m : classNode.methods) {
					if(renderLivingLabelMethod.equals(m.name)) {
						InsnList toAdd = new InsnList();
						toAdd.add(new LabelNode(new Label()));
						toAdd.add(new VarInsnNode(ALOAD, 2));
						toAdd.add(new VarInsnNode(ALOAD, 1));
						toAdd.add(new MethodInsnNode(INVOKESTATIC, "thelm/textevents/events/TextEventFactory", "onRenderEntityLabel", "(Ljava/lang/String;Lnet/minecraft/entity/Entity;)Ljava/lang/String;", false));
						toAdd.add(new VarInsnNode(ASTORE, 2));

						boolean primed = false;
						for(int i = 0; i < m.instructions.size(); i++) {
							AbstractInsnNode next = m.instructions.get(i);

							if(!primed && next.getOpcode() == IFGT && next instanceof JumpInsnNode) {
								primed = true;
								continue;
							}

							if(primed && next instanceof LabelNode) {
								m.instructions.insertBefore(next, toAdd);
								done = true;
								break;
							}
						}
						break;
					}
				}
				if(!done) {
					TextEvents.LOGGER.info("Transforming failed.");
				}
			});
		}
		if(tileEntitySignRendererClass.equals(transformedName)) {
			return transform(basicClass, tileEntitySignRendererClass, renderMethod, classNode->{
				boolean done = false;
				for(MethodNode m : classNode.methods) {
					if(renderMethod.equals(m.name)) {
						InsnList toAdd = new InsnList();
						toAdd.add(new LabelNode(new Label()));
						toAdd.add(new VarInsnNode(ALOAD, 17));
						toAdd.add(new VarInsnNode(ALOAD, 1));
						toAdd.add(new MethodInsnNode(INVOKESTATIC, "thelm/textevents/events/TextEventFactory", "onRenderSignText", "(Lnet/minecraft/util/text/ITextComponent;Lnet/minecraft/tileentity/TileEntitySign;)Lnet/minecraft/util/text/ITextComponent;", false));
						toAdd.add(new VarInsnNode(ASTORE, 17));

						int count = 0;
						for(int i = 0; i < m.instructions.size(); i++) {
							AbstractInsnNode next = m.instructions.get(i);

							if(count != 3 && next.getOpcode() == GETFIELD && next instanceof FieldInsnNode) {
								if(signTextField.equals(((FieldInsnNode)next).name)) {
									count++;
									continue;
								}
							}

							if(count == 3 && next instanceof LabelNode) {
								m.instructions.insertBefore(next, toAdd);
								done = true;
								break;
							}
						}
						break;
					}
				}
				if(!done) {
					TextEvents.LOGGER.info("Transforming failed.");
				}
			});
		}
		if(netHandlerPlayClientClass.equals(transformedName)) {
			return transform(basicClass, netHandlerPlayClientClass, new ObfSafeName(handleTitleMethod, handlePlayerListItemMethod), classNode->{
				int done = 0;
				for(MethodNode m : classNode.methods) {
					if(handleTitleMethod.equals(m.name)) {
						InsnList toAdd = new InsnList();
						toAdd.add(new LabelNode(new Label()));
						toAdd.add(new VarInsnNode(ALOAD, 5));
						toAdd.add(new VarInsnNode(ALOAD, 2));
						toAdd.add(new MethodInsnNode(INVOKESTATIC, "thelm/textevents/events/TextEventFactory", "onClientReceivedTitle", "(Ljava/lang/String;Lnet/minecraft/network/play/server/SPacketTitle$Type;)Ljava/lang/String;", false));
						toAdd.add(new VarInsnNode(ASTORE, 5));

						boolean primed = false;
						for(int i = 0; i < m.instructions.size(); i++) {
							AbstractInsnNode next = m.instructions.get(i);

							if(!primed && next.getOpcode() == ASTORE && next instanceof VarInsnNode) {
								if(((VarInsnNode)next).var == 5) {
									primed = true;
									continue;
								}
							}

							if(primed && next instanceof LabelNode) {
								m.instructions.insertBefore(next, toAdd);
								done++;
								break;
							}
							else {
								primed = false;
							}
						}
					}
					if(handlePlayerListItemMethod.equals(m.name)) {
						InsnList toAdd = new InsnList();
						toAdd.add(new VarInsnNode(ALOAD, 3));
						toAdd.add(new MethodInsnNode(INVOKESTATIC, "thelm/textevents/events/TextEventFactory", "onClientReceivedPlayerName", "(Lnet/minecraft/util/text/ITextComponent;Lnet/minecraft/network/play/server/SPacketPlayerListItem$AddPlayerData;)Lnet/minecraft/util/text/ITextComponent;", false));

						for(int i = 0; i < m.instructions.size(); i++) {
							AbstractInsnNode next = m.instructions.get(i);

							if(next.getOpcode() == INVOKEVIRTUAL && next instanceof MethodInsnNode) {
								if(getDisplayNameMethodAddPlayerData.equals(((MethodInsnNode)next).name)) {
									m.instructions.insert(next, toAdd);
									done++;
									break;
								}
							}
						}
						break;
					}
				}
				if(done != 2) {
					TextEvents.LOGGER.info("Transforming failed.");
				}
			});
		}
		if(bossInfoClientClass.equals(transformedName)) {
			return transform(basicClass, bossInfoClientClass, updateFromPacketMethod, classNode->{
				int done = 0;
				for(MethodNode m : classNode.methods) {
					if(initMethod.equals(m.name)) {
						AbstractInsnNode toAdd = new MethodInsnNode(INVOKESTATIC, "thelm/textevents/events/TextEventFactory", "onClientReceivedBossName", "(Lnet/minecraft/util/text/ITextComponent;)Lnet/minecraft/util/text/ITextComponent;", false);

						for(int i = 0; i < m.instructions.size(); i++) {
							AbstractInsnNode next = m.instructions.get(i);

							if(next.getOpcode() == INVOKEVIRTUAL && next instanceof MethodInsnNode) {
								if(getNameMethod.equals(((MethodInsnNode)next).name)) {
									m.instructions.insert(next, toAdd);
									done++;
									break;
								}
							}
						}
					}
					if(updateFromPacketMethod.equals(m.name)) {
						AbstractInsnNode toAdd = new MethodInsnNode(INVOKESTATIC, "thelm/textevents/events/TextEventFactory", "onClientReceivedBossName", "(Lnet/minecraft/util/text/ITextComponent;)Lnet/minecraft/util/text/ITextComponent;", false);

						for(int i = 0; i < m.instructions.size(); i++) {
							AbstractInsnNode next = m.instructions.get(i);

							if(next.getOpcode() == INVOKEVIRTUAL && next instanceof MethodInsnNode) {
								if(getNameMethod.equals(((MethodInsnNode)next).name)) {
									m.instructions.insert(next, toAdd);
									done++;
									break;
								}
							}
						}
					}
				}
				if(done != 2) {
					TextEvents.LOGGER.info("Transforming failed.");
				}
			});
		}
		if(itemStackClass.equals(transformedName)) {
			return transform(basicClass, itemStackClass, getDisplayNameMethodItemStack, classNode->{
				boolean done = false;
				for(MethodNode m : classNode.methods) {
					if(getDisplayNameMethodItemStack.equals(m.name)) {
						InsnList toAdd = new InsnList();
						toAdd.add(new LabelNode(new Label()));
						toAdd.add(new VarInsnNode(ALOAD, 1));
						toAdd.add(new VarInsnNode(ALOAD, 0));
						toAdd.add(new MethodInsnNode(INVOKESTATIC, "thelm/textevents/events/TextEventFactory", "onGetItemDisplayName", "(Lnet/minecraft/nbt/NBTTagCompound;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/nbt/NBTTagCompound;", false));
						toAdd.add(new VarInsnNode(ASTORE, 1));

						boolean primed = false;
						for(int i = 0; i < m.instructions.size(); i++) {
							AbstractInsnNode next = m.instructions.get(i);

							if(!primed && next.getOpcode() == INVOKEVIRTUAL && next instanceof MethodInsnNode) {
								if(getSubCompoundMethod.equals(((MethodInsnNode)next).name)) {
									primed = true;
									continue;
								}
							}

							if(primed && next instanceof LabelNode) {
								m.instructions.insertBefore(next, toAdd);
								done = true;
								break;
							}
						}
						break;
					}
				}
				if(!done) {
					TextEvents.LOGGER.info("Transforming failed.");
				}
			});
		}
		if(networkPlayerInfoClass.equals(transformedName)) {
			return transform(basicClass, networkPlayerInfoClass, new ObfSafeName(initMethod, initMethod), classNode->{
				boolean done = false;
				for(MethodNode m : classNode.methods) {
					if(initMethod.equals(m.name) && initMethodDesc.equals(m.desc)) {
						InsnList toAdd = new InsnList();
						toAdd.add(new VarInsnNode(ALOAD, 1));
						toAdd.add(new MethodInsnNode(INVOKESTATIC, "thelm/textevents/events/TextEventFactory", "onClientReceivedPlayerName", "(Lnet/minecraft/util/text/ITextComponent;Lnet/minecraft/network/play/server/SPacketPlayerListItem$AddPlayerData;)Lnet/minecraft/util/text/ITextComponent;", false));

						for(int i = 0; i < m.instructions.size(); i++) {
							AbstractInsnNode next = m.instructions.get(i);

							if(next.getOpcode() == INVOKEVIRTUAL && next instanceof MethodInsnNode) {
								if(getDisplayNameMethodAddPlayerData.equals(((MethodInsnNode)next).name)) {
									m.instructions.insert(next, toAdd);
									done = true;
									break;
								}
							}
						}
						break;
					}
				}
				if(!done) {
					TextEvents.LOGGER.info("Transforming failed.");
				}
			});
		}
		return basicClass;
	}
}

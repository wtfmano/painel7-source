package com.google.firebase.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: com.google.firebase:firebase-common@@19.0.0 */
/* loaded from: classes.dex */
public class CycleDetector {
    CycleDetector() {
    }

    /* compiled from: com.google.firebase:firebase-common@@19.0.0 */
    /* loaded from: classes.dex */
    public static class Dep {
        private final Class<?> anInterface;
        private final boolean set;

        private Dep(Class<?> anInterface, boolean set) {
            this.anInterface = anInterface;
            this.set = set;
        }

        public boolean equals(Object obj) {
            if (obj instanceof Dep) {
                Dep dep = (Dep) obj;
                return dep.anInterface.equals(this.anInterface) && dep.set == this.set;
            }
            return false;
        }

        public int hashCode() {
            int h = 1000003 ^ this.anInterface.hashCode();
            return (h * 1000003) ^ Boolean.valueOf(this.set).hashCode();
        }
    }

    /* compiled from: com.google.firebase:firebase-common@@19.0.0 */
    /* loaded from: classes.dex */
    public static class ComponentNode {
        private final Component<?> component;
        private final Set<ComponentNode> dependencies = new HashSet();
        private final Set<ComponentNode> dependents = new HashSet();

        ComponentNode(Component<?> component) {
            this.component = component;
        }

        void addDependency(ComponentNode node) {
            this.dependencies.add(node);
        }

        void addDependent(ComponentNode node) {
            this.dependents.add(node);
        }

        Set<ComponentNode> getDependencies() {
            return this.dependencies;
        }

        void removeDependent(ComponentNode node) {
            this.dependents.remove(node);
        }

        Component<?> getComponent() {
            return this.component;
        }

        boolean isRoot() {
            return this.dependents.isEmpty();
        }

        boolean isLeaf() {
            return this.dependencies.isEmpty();
        }
    }

    public static void detect(List<Component<?>> components) {
        Set<ComponentNode> graph = toGraph(components);
        Set<ComponentNode> roots = getRoots(graph);
        int numVisited = 0;
        while (!roots.isEmpty()) {
            ComponentNode node = roots.iterator().next();
            roots.remove(node);
            numVisited++;
            for (ComponentNode dependent : node.getDependencies()) {
                dependent.removeDependent(node);
                if (dependent.isRoot()) {
                    roots.add(dependent);
                }
            }
        }
        if (numVisited == components.size()) {
            return;
        }
        List<Component<?>> componentsInCycle = new ArrayList<>();
        for (ComponentNode node2 : graph) {
            if (!node2.isRoot() && !node2.isLeaf()) {
                componentsInCycle.add(node2.getComponent());
            }
        }
        throw new DependencyCycleException(componentsInCycle);
    }

    private static Set<ComponentNode> toGraph(List<Component<?>> components) {
        Set<ComponentNode> depComponents;
        Map<Dep, Set<ComponentNode>> componentIndex = new HashMap<>(components.size());
        for (Component<?> component : components) {
            ComponentNode node = new ComponentNode(component);
            for (Class<?> anInterface : component.getProvidedInterfaces()) {
                Dep cmp = new Dep(anInterface, !component.isValue());
                if (!componentIndex.containsKey(cmp)) {
                    componentIndex.put(cmp, new HashSet<>());
                }
                Set<ComponentNode> nodes = componentIndex.get(cmp);
                if (!nodes.isEmpty() && !cmp.set) {
                    throw new IllegalArgumentException(String.format("Multiple components provide %s.", anInterface));
                }
                nodes.add(node);
            }
        }
        for (Set<ComponentNode> componentNodes : componentIndex.values()) {
            for (ComponentNode node2 : componentNodes) {
                for (Dependency dependency : node2.getComponent().getDependencies()) {
                    if (dependency.isDirectInjection() && (depComponents = componentIndex.get(new Dep(dependency.getInterface(), dependency.isSet()))) != null) {
                        for (ComponentNode depComponent : depComponents) {
                            node2.addDependency(depComponent);
                            depComponent.addDependent(node2);
                        }
                    }
                }
            }
        }
        HashSet<ComponentNode> result = new HashSet<>();
        for (Set<ComponentNode> componentNodes2 : componentIndex.values()) {
            result.addAll(componentNodes2);
        }
        return result;
    }

    private static Set<ComponentNode> getRoots(Set<ComponentNode> components) {
        Set<ComponentNode> roots = new HashSet<>();
        for (ComponentNode component : components) {
            if (component.isRoot()) {
                roots.add(component);
            }
        }
        return roots;
    }
}

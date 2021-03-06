﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.CodeAnalysis;
using Microsoft.CodeAnalysis.Diagnostics;

using Microsoft.CodeAnalysis.CSharp.Syntax;
using Microsoft.CodeAnalysis.CSharp;
using Microsoft.CodeAnalysis.Text;
using System.Collections.Immutable;
using System.Threading;
using System.Text.RegularExpressions;

namespace NSonarQubeAnalyzer
{
    [DiagnosticAnalyzer(LanguageNames.CSharp)]
    public class MethodName : ISyntaxNodeAnalyzer<SyntaxKind>
    {
        internal const string DiagnosticId = "MethodName";
        internal const string Description = "Method name should comply with a naming convention";
        internal const string MessageFormat = "Rename this method to match the regular expression: {0}";
        internal const string Category = "SonarQube";
        internal const DiagnosticSeverity Severity = DiagnosticSeverity.Warning;

        internal static DiagnosticDescriptor Rule = new DiagnosticDescriptor(DiagnosticId, Description, MessageFormat, Category, Severity, true);

        public ImmutableArray<DiagnosticDescriptor> SupportedDiagnostics { get { return ImmutableArray.Create(Rule); } }

        public ImmutableArray<SyntaxKind> SyntaxKindsOfInterest { get { return ImmutableArray.Create(SyntaxKind.MethodDeclaration); } }

        public string Convention;

        public void AnalyzeNode(SyntaxNode node, SemanticModel semanticModel, Action<Diagnostic> addDiagnostic, AnalyzerOptions options, CancellationToken cancellationToken)
        {
            var identifier = ((MethodDeclarationSyntax)node).Identifier;

            if (!Regex.IsMatch(identifier.Text, Convention))
            {
                addDiagnostic(Diagnostic.Create(Rule, identifier.GetLocation(), Convention));
            }
        }
    }
}
